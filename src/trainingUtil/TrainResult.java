package trainingUtil;

/**
 * class representing the result of one training iteration
 */
public class TrainResult
{
	double avgError;
	boolean correct;
	
	/**
	 * default constructor
	 * @param d - average error rate as a double
	 * @param b - binary error(right or wrong)
	 */
	public TrainResult(double d, boolean b)
	{
		this.avgError = d;
		this.correct = b;
	}
	
	public double getAvgError() {return this.avgError;}
	public boolean getCorrect() {return this.correct;}
}
