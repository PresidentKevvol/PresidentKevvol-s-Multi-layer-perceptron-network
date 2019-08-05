package activationFunctions;


/**
 * representation of a 'leaky' ReLU function<br>
 * for the x < 0 part, instead of capping it to 0,<br>
 * scale it to a small amount(say, 0.01)<br>
 * so that the neuron would not be dead in gradient descent<br>
 * when f'(x) = 0 when x < 0 in the gradient
 */
public class ReLU_leaky implements ActivationFunction
{
	public double f(double x)
	{
		return (x > 0)? x : x * 0.01;
	}
	
	public double fPrime(double x)
	{
		return (x > 0)? 1 : 0.01;
	}
}
