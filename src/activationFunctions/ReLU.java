package activationFunctions;

/**
 * representation of a ReLU function<br>
 * aka Rectified Linear Units<br>
 * where f(x) = x when x > 0, and 0 if x < 0
 */
public class ReLU implements ActivationFunction
{
	public double f(double x)
	{
		return (x > 0)? x : 0;
	}

	public double fPrime(double x)
	{
		return (x > 0)? 1 : 0;
	}

}
