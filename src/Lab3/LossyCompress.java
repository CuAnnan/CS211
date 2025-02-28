package Lab3;

public class LossyCompress
{
	
	
	
	public static String rle(String toEncode)
	{
		String out = "";
		char lastChar = toEncode.charAt(0);
		int tally = 1;
		for(int i = 1;  i < toEncode.length(); i++)
		{
			if(toEncode.charAt(i) != lastChar)
			{
				out += tally+""+lastChar;
				lastChar = toEncode.charAt(i);
				tally = 0;
			}
			
			tally++;
		}
		out += tally+""+lastChar;
		return out;
	}
	
	public static String superCrunch(String t)
	{
		String best = rle(t);
		int maxCharsToRemove = t.length() / 2;
		int leftPointer = 0;
		int charsToRemove = 1;
		
		while(charsToRemove < maxCharsToRemove)
		{
			leftPointer = 0;
			while(leftPointer < t.length() - charsToRemove + 1)
			{
				String toEncode = t.substring(0, leftPointer) + t.substring(leftPointer + charsToRemove, t.length());
				String encoded = rle(toEncode);
				if(encoded.length()  < best.length())
				{
					best = encoded;
				}
				
				leftPointer++;
			}
			charsToRemove++;
		}
		
		return best;
	}
	
	public static void main(String args[])
	{
		System.out.println(superCrunch("1112223341111"));
	}
}
