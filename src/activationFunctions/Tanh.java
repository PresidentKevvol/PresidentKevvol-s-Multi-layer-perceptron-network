package activationFunctions;

/**
 * implementation of tanh function<br>
 * (tangent of hyperbola)<br>
 *  -1 < f(x) < 1 for any real x
 */
public class Tanh implements ActivationFunction
{
	public double f(double x)
	{
		return Math.tanh(x);
	}
	
	public double fPrime(double x)
	{
		//this should be faster than using Math.pow()
		double tanh = Math.tanh(x);
		return 1 - (tanh * tanh);
	}
}
