package activationFunctions;

/**
 * implementation of the ActivationFunction interface
 * represent a vertically scaled arctangent function
 * which ranges from -1 to 1
 */
public class Arctan implements ActivationFunction
{
	@Override
	public double f(double x)
	{
		//System.out.println("Arctan.f(): called");
		return 2 * Math.atan(x) / Math.PI;
	}

	@Override
	public double fPrime(double x)
	{
		//System.out.println("Arctan.fPrime(): called");
		return 2 / (Math.PI * (x * x + 1));
	}
}
