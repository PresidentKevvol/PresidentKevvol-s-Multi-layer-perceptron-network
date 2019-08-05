package maine;

/**
 * object for denoting what a neuron needs update
 * @author Acer
 *
 */
public class NeuronUpdateNotation
{
	double[] weights;
	double bias;
	
	/**
	 * standard constructor
	 * @param w weights
	 * @param b bias
	 */
	public NeuronUpdateNotation(double[] w, double b)
	{
		this.weights = w;
		this.bias = b;
	}
	
	/**
	 * getter for a (delta)weight
	 * @param i index of synapse
	 * @return delta weight value
	 */
	public double getWeight(int i){return this.weights[i];}
	
	/**
	 * getter for the (delta)bias
	 * @return delta bias value
	 */
	public double getBias(){return this.bias;}
}
