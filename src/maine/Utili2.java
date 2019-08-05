package maine;

public class Utili2
{
	/**
	 * the sigmoid function for neural networks
	 * when input approaches positive infinity, output get close to 1
	 * when input approaches negative infinity, output become more close to 0
	 * @param x input
	 * @return output
	 */
	public static double sigmoid(double x)
	{
		double r = 1/(1 + Math.exp(-x));
		
		//debug
		if(Double.isNaN(r))
		{
			System.out.println("Utili2.sigmoid(): sigmoid(x) is NaN");
			Thread.dumpStack();
			
			System.out.println("Utili2.sigmoid(): program ending");
			//exit
			System.exit(0);
		}
		
		return r;
	}
	
	/**
	 * the first derivative of the sigmoid function
	 * peaks at 0.25 when x=0
	 * @param x input
	 * @return output
	 */
	public static double sigmoidPrime(double x)
	{
		if(x > 600)
		{
			//x being too big -> overflow from 64bit double -> number becomes NaN
			return 0;
		}
		
		double r = Math.exp(x) / Math.pow((Math.exp(x) + 1), 2);
		
		//debug
		if(Double.isNaN(r))
		{
			System.out.println("Utili2.sigmoidPrime(): sigmoid'(x) is NaN, x: " + x);
			Thread.dumpStack();
			
			System.out.println("Utili2.sigmoidPrime(): program ending");
			//exit
			System.exit(0);
		}
		
		return r;
	}
}
