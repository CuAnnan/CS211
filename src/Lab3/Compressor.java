package Lab3;
import LinkedLists.LinkedList;
import LinkedLists.Node;


class CompressedLetterRun
{
	char letter;
	int  runLength;
	int  potentialLengthReduction;
	int  potentialNodeReduction;
	int  overFlow;
	
	public CompressedLetterRun(char letter)
	{
		this.letter = letter;
		this.runLength = 1;
		this.potentialLengthReduction = 1;
		this.potentialNodeReduction = 1;
		this.overFlow = 0;
	}
	
	public void increaseRunLength()
	{
		this.runLength++;
		this.potentialLengthReduction++;
	}
	
	
	public String toString()
	{
		return this.runLength+""+this.letter;
	}
	
	public int getEncodedLength()
	{
		return this.toString().length();
	}
	
	public String debug()
	{
		return "--"+this.letter+":"+this.runLength+":"+this.potentialLengthReduction+":"+this.potentialNodeReduction+":"+this.overFlow;
	}
}

public class Compressor
{
	private String toCompress;
	private LinkedList<CompressedLetterRun> letterRuns;
	private boolean compressed;
	private int maximumCrunch;
	private Compressor crunched;
	@SuppressWarnings("unused")
	private int encodedLength;
	
	
	public Compressor(String toCompress)
	{
		this.setToCompress(toCompress);
		this.compressed = false;
		this.crunched = null;
		this.letterRuns = new LinkedList<CompressedLetterRun>();		
	}
	
	public Compressor()
	{
		this.compressed = true;
		this.crunched = this;
		this.letterRuns = new LinkedList<CompressedLetterRun>();
	}
	
	void setToCompress(String toCompress)
	{
		this.toCompress = toCompress;
		// it doesn't matter that this is rounded down. Removing 5 characters from 11 is the closest you can get to 5.5 characters without going over.
		this.maximumCrunch = toCompress.length() / 2; 
	}
	
	/**
	 * This just creates a linked list of number letter pairs representing the compressed RLE.
	 * It's a little RAM heavier than just manipulating the string, but I'm relatively certain the ram overhead results in a better strategy overall.
	 * @return
	 */
	Compressor compress()
	{
		if(this.compressed)
		{
			return this;
		}
		CompressedLetterRun current = new CompressedLetterRun(this.toCompress.charAt(0));
		this.letterRuns.add(current);
		
		for(int i = 1; i < this.toCompress.length(); i++)
		{
			if(current.letter != this.toCompress.charAt(i))
			{
				this.encodedLength += current.getEncodedLength();
				current = new CompressedLetterRun(this.toCompress.charAt(i));
				this.letterRuns.add(current);
			}
			else
			{
				current.increaseRunLength();
			}
		}
		this.encodedLength += current.getEncodedLength();
		this.compressed = true;
		return this;
	}
	
	void findBestCompressionCandidates()
	{
		Node<CompressedLetterRun> currentNode = this.letterRuns.getHead();
		// Start with the first node and see how many consecutive nodes we can remove based on the maximum removal length
		while(currentNode != null)
		{
			boolean canStillScan = true;
			CompressedLetterRun current = currentNode.getContents();
			if(current.potentialLengthReduction < this.maximumCrunch)
			{
				int reduction = current.potentialLengthReduction;
				Node<CompressedLetterRun> next = currentNode.getNext();
				canStillScan = next != null;
				while(canStillScan)
				{
					if(current.potentialLengthReduction + next.getContents().runLength < this.maximumCrunch)
					{
						//System.out.println("Can add "+next.getContents().potentialLengthReduction +" to "+current.potentialLengthReduction+" for "+current.letter+" and still be below "+this.maximumCrunch);
						current.potentialLengthReduction += next.getContents().potentialLengthReduction;
						current.potentialNodeReduction++;
						reduction += next.getContents().potentialLengthReduction;
						next = next.getNext();
					}
					else
					{
						CompressedLetterRun nextRun = next.getContents();
						// only consider borrowing digits from next if it has more than ten in a run, otherwise it cannot be worth doing
						if(nextRun.runLength >= 10)
						{
							int oomNext = nextRun.potentialLengthReduction;
							int availableReduction = this.maximumCrunch - reduction;
							int resultingOomNext = (int)Math.log10(nextRun.runLength - availableReduction);
							if(resultingOomNext < oomNext)
							{
								//System.out.println("Can add "+ (oomNext - resultingOomNext) +" to "+current.potentialLengthReduction+" for "+current.letter+" and still be below "+this.maximumCrunch);
								nextRun.overFlow = availableReduction;
								reduction = this.maximumCrunch;
								current.potentialLengthReduction += availableReduction;
							}
						}
						canStillScan = false;
					}
					if(next == null)
					{
						canStillScan = false;
					}
				}
				
			}
			currentNode = currentNode.getNext();
		}
	}
	
	/**
	 * Lossy compression
	 */
	Compressor crunch()
	{
		if(this.crunched != null)
		{
			return this.crunched;
		}
		this.compress();
		this.findBestCompressionCandidates();
		
		// assume it will be the first
		Node<CompressedLetterRun> firstToRemove = this.letterRuns.getHead();
		// find the node with the biggest yield
		Node<CompressedLetterRun> currentNode = this.letterRuns.getHead();
		while(currentNode != null)
		{
			if(currentNode.getContents().potentialNodeReduction > firstToRemove.getContents().potentialNodeReduction)
			{
				firstToRemove = currentNode;
			}
			currentNode = currentNode.getNext();
		}
		
		// build our new compressor
		Compressor crunched = new Compressor();
		crunched.setToCompress(this.toCompress);
		currentNode = this.letterRuns.getHead();
		
		int skippedNodes = 0;
		int nodesToSkip = firstToRemove.getContents().potentialNodeReduction;
		boolean startedSkipping = false;
		
		while(currentNode != null)
		{
			if(!startedSkipping && currentNode == firstToRemove)
			{
				startedSkipping = true;
			}
			
			if(startedSkipping && skippedNodes < nodesToSkip)
			{
				skippedNodes ++;
			}
			else
			{
				crunched.letterRuns.add(currentNode.getContents());
			}
			currentNode = currentNode.getNext();
		}
		
		this.crunched = crunched;
		return crunched;
	}
	
	public void debugCrunch()
	{
		this.crunch();
		String out = "Maximum crunch: "+this.maximumCrunch;
		Node<CompressedLetterRun> current = this.letterRuns.getHead();
		while(current != null)
		{
			out += current.getContents().debug();
			current = current.getNext();
		}
		System.out.println(out);
	}
	
	public String getCrunchedRLE()
	{
		this.crunch();
		String out = "";
		Node<CompressedLetterRun> current = this.crunched.letterRuns.getHead();
		while(current != null)
		{
			out += current.getContents().toString();
			current = current.getNext();
		}
		return out;
	}
	
	public String getRLE()
	{
		this.compress();
		String out = "";
		Node<CompressedLetterRun> current = this.letterRuns.getHead();
		System.out.println(this.toCompress);
		
		while(current != null)
		{
			out += current.getContents().toString();
			current = current.getNext();
		}
		return out;
	}
	
	public static void main(String[] args)
	{
		Compressor c = new Compressor("aaaaaaaaabbbbbeeeeefffggi");
//		c.compress();
		System.out.println(c.getRLE());
		c.debugCrunch();
		System.out.println(c.getCrunchedRLE());
		
	}
}
