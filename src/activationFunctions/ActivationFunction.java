package activationFunctions;

/**
 * the interface for an activation function used in a neuron
 */
public interface ActivationFunction
{
	/**
	 * the activation function
	 * @param x - input, the 'x' in 'f(x)'
	 * @return the output
	 */
	public double f(double x);
	
	/**
	 * the first derivative of the activation function
	 * @param x - input, as in 'x' of "f'(x)"
	 * @return the output
	 */
	public double fPrime(double x);
}
