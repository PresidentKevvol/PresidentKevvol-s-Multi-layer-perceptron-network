package trainingObjects;

import java.util.Random;
import java.util.Vector;

/**
 * object for the collection of data entries
 */
public class DataEntryCollection
{
	Vector<DataEntry> entries;
	double generatePositiveRate;
	Random rng;
	
	/**
	 * default constructor<br>
	 * initializes an empty array
	 * @param s - the rate of generating a TrainingSample that answer is 1.0
	 * (useful for controlling training)
	 */
	public DataEntryCollection(double s)
	{
		this.entries = new Vector<DataEntry>();
		this.generatePositiveRate = s;
		this.rng = new Random();
	}
	
	/**
	 * getter method for the total number of raw entries
	 * @return number of raw entries
	 */
	public int getSize()
	{
		return this.entries.size();
	}
	
	/**
	 * get a specific entry from vector
	 * @param i - index of entry
	 * @return entry with corresponding index at the vector
	 */
	public DataEntry get(int i)
	{
		return this.entries.elementAt(i);
	}
	
	/**
	 * adds element from another vector into the list
	 * @param d - the vector to be added
	 */
	public void add(Vector<DataEntry> d)
	{
		this.entries.addAll(d);
	}
	
	/**
	 * generate one sample from list at random
	 * @return the sample generated
	 */
	public TrainingSample getOneSample()
	{
		//determine if use sample with positive answer
		double ran = rng.nextDouble();
		//random element from list
		int ind = rng.nextInt(this.entries.size());
		DataEntry entri = this.entries.get(ind);
		
		if(ran < this.generatePositiveRate)
		{//generate a sample which answer is 1.0 if generated number is < rate
			return entri.generatePositiveSample();
		}
		else
		{
			//sample range from G3(35 - 28 = 7) to B5(63 - 28 = 35)
			return entri.generateNegativeSample(7, 35);
		}
	}
	
	/**
	 * get an array of training samples
	 * @param s - size of array(number of sample requesting)
	 * @return an array of size s containg generated samples
	 */
	public TrainingSample[] getSampleArray(int s)
	{
		TrainingSample[] result = new TrainingSample[s];
		
		//generate sample and put them into array
		for(int ct=0; ct<s; ct++)
		{
			result[ct] = this.getOneSample();
		}
		
		return result;
	}
}
