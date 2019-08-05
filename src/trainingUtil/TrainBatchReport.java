package trainingUtil;

/**
 * object representing a 'report' of a batch training
 */
public class TrainBatchReport
{
	double avgError;
	int totalCorrect;
	int totalSampleSize;
	
	/**
	 * standard constructor
	 * @param d - average error rate
	 * @param i - total number of correct
	 * @param t - total number in this batch
	 */
	public TrainBatchReport(double d, int i, int t)
	{
		this.avgError = d;
		this.totalCorrect = i;
		this.totalSampleSize = t;
	}
	
	//getters
	public double getAvgError() {return this.avgError;}
	public int getTotalCorrect() {return this.totalCorrect;}
	public int getTotalSampleSize() {return this.totalSampleSize;}
	
	//special value getters
	public double getCorrectRate() {return (double)this.totalCorrect / (double)this.totalSampleSize;}
	public String getCorrectCountDescription() {return "(" + this.totalCorrect + "/" + this.totalSampleSize + ")";}
}
