package trainingObjects;

/**
 * object describing a training sample
 */
public class TrainingSample
{
	/**
	 * the inputArray is the input vector for one training sample
	 */
	double[] inputArray;
	/**
	 * and the output array should only be one element,
	 * with a value of either 1.0 or 0.0
	 */
	double[] outputArray;
	
	/**
	 * default constructor for one training sample
	 * @param d1 - input array
	 * @param d2 - output array
	 */
	public TrainingSample(double[] d1, double[] d2)
	{
		this.inputArray = d1;
		this.outputArray = d2;
	}
	
	//getters
	public double[] getInput() {return this.inputArray;}
	public double[] getOutput() {return this.outputArray;}
}
