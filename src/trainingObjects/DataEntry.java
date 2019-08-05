package trainingObjects;

import java.util.Arrays;
import java.util.Random;

/**
 * object for a single entry of data<br>
 * for training the neural network
 */
public class DataEntry
{
	int correctIndex;
	double[] dataVector;
	
	/**
	 * default constructor putting values into the object
	 * @param i - index for correct answer
	 * @param d - entire data array
	 */
	public DataEntry(int i, double[] d)
	{
		this.correctIndex = i;
		this.dataVector = d;
	}
	
	/**
	 * getter method for one data value
	 * @param i - index of array
	 * @return the value in the index
	 */
	public double get(int i)
	{
		return this.dataVector[i];
	}
	
	/**
	 * getter method for the correct answer entry
	 * @return the correct(target) value for this entry
	 */
	public int getCorrect()
	{
		return this.correctIndex;
	}
	
	/**
	 * method for getting a slice of the array
	 * @param startInd - starting index
	 * @param size - size of slice
	 * @return the sliced array
	 */
	public double[] getSection(int startInd, int size)
	{
		return Arrays.copyOfRange(this.dataVector, startInd, startInd + size);
	}
	
	/**
	 * method for compressing an array's value into ratio of the first element<br>
	 * so first element will always be 1.0<br>
	 * this is used for generating training samples
	 * @param d - original array
	 * @return modified array
	 */
	public double[] compressArrayValues(double[] d)
	{
		int len = d.length;
		double[] result = new double[len];
		//get first element
		double first = d[0];
		
		//transform elements
		for(int ct=0; ct<len; ct++)
		{
			result[ct] = d[ct] / first;
		}
		
		return result;
	}
	
	/**
	 * generates one training sample that is positive in answer
	 * @return a training sample with positive answer(output == {1.0})
	 */
	public TrainingSample generatePositiveSample()
	{
		double[] section = this.getSection(this.getCorrect(), StaticValues.InputArraySize);
		double[] input = this.compressArrayValues(section);
		//first node meaning it is the correct note
		double[] output = {1.0, -1.0};
		return new TrainingSample(input, output);
	}
	
	/**
	 * generates one training sample that is negative in answer
	 * @param min - minimum index that can generate sample with(inclusive)
	 * @param max - maximum index that can generate sample with(inclusive)
	 * @return a training sample with positive answer(output == {0.0})
	 */
	public TrainingSample generateNegativeSample(int min, int max)
	{
		int ran;
		Random r = new Random();
		
		//generate a number for starting index that is not the correct index
		for(;;)
		{
			//int in [min, max]
			int i = r.nextInt(max - min + 1) + min;
			
			if(i != this.correctIndex)
			{
				ran = i;
				break;
			}
		}
		
		//make the TrainingSample object
		double[] section = this.getSection(ran, StaticValues.InputArraySize);
		double[] input = this.compressArrayValues(section);
		double[] output = {-1.0, 1.0};
		
		return new TrainingSample(input, output);
	}
}
