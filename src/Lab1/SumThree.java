package Lab1;

import java.util.Arrays;
import java.util.Random;

/*
 * A struct-like to hold the result and the values used to make it.
 */
class Result
{
	int num1, num2, num3, result;
	
	Result(int num1, int num2, int num3)
	{
		this.num1 = num1;
		this.num2 = num2;
		this.num3 = num3;
		this.result = num1 + num2 + num3;
	}
}

public class SumThree
{
	// generate a bunch of numbers between -range and range
	final static int range = 50;
	final static int target = 120;
	
	
	public static Result closestSumThree(int[] array, int target)
	{
		// sort it. Which is less than o(n^2) because it's a pivot sort
		Arrays.sort(array);
		
		// default the current difference to "infinity"
		int currentDiff = Integer.MAX_VALUE;
		// This is the result of the algorithm, to be given a proper value later
		Result result = null;
		
		for(int i = 0; i < array.length - 3; i++)
		{
			// start by looking at the ith, i+1th and last element
			int left = i + 1;
			int right = array.length - 1;
			
			while(left < right)
			{
				// guess that this is the result
				Result guess = new Result(array[i], array[left], array[right]);
				// get the difference between that guess and the target
				int guessDiff = Math.abs(target - guess.result);
				// if it's closer use it
				if(guessDiff < currentDiff)
				{
					result = guess;
					currentDiff = guessDiff;
				}
				// if it's smaller, we increase the left index by one to try and nudge closer by increasing our guess
				else if(guess.result < target)
				{
					left++;
				}
				// otherwise we decrease the right index by one to nudge it closer by reducing our guess
				else
				{
					right--;
				}
			}
		}
		
		return result;
	}
	
	
	public static void main(String[] args)
	{
		
		// generate a search array of 100 numbers approximately within the appropriate search space
		int[] searchSpace = new int[100];
		Random r = new Random();
		
		for(int i = 0; i < 100; i++)
		{
			searchSpace[i] = r.nextInt(range * 2) - range;
		}
		
		// run the algorithm
		Result closest = closestSumThree(searchSpace, target);
		
		System.out.println(
			String.format(
				"%d (%d + %d + %d)",
				closest.result,
				closest.num1, closest.num2, closest.num3 
			)
		);
	}
}
