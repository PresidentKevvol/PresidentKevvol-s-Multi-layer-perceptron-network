package trainingObjects;

public class StaticValues
{
	/**
	 * the lowest note in the dataset, used as a reference point for array indices<br>
	 * the base note(lowest note) is C3(28), so remember to subtract 
	 * the number to get accurate array index
	 */
	public static final int lowestNote = 28;
	
	/**
	 * the size of the input array of the network<br>
	 * used for creating the network and extracting training samples<br>
	 * defined as reference point and 24 semitone above
	 */
	public static final int InputArraySize = 25;
	
	/**
	 * the rate of feeding positive(target output == {1.0}) training sample when training
	 */
	public static final double positiveRate = 1.0 / 30;
	
	/**
	 * learning rate used for gradient descent
	 */
	public static final double learningRate = 0.02;
}
