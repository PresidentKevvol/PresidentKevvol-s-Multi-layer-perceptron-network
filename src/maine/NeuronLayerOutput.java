package maine;

public class NeuronLayerOutput
{
	/**
	 * size of the output vector
	 */
	int size;
	
	double[] activation;
	double[] output;
	
	/**
	 * constructor
	 * @param i size of output vector(how many neurons)
	 */
	public NeuronLayerOutput(int i)
	{
		this.size = i;
	}
	
	/**
	 * constructor
	 * gives an array of activations and calculate the output as well
	 * @param act activation from neuron
	 */
	private NeuronLayerOutput(double[] act)
	{
		this.size = act.length;
		this.activation = act;
		this.output = new double[size];
		
		for(int ct=0; ct<size; ct++)
		{
			output[ct] = Utili2.sigmoid(activation[ct]);
		}
	}
	
	/**
	 * constructor with given array of activation and output values
	 * @param act - activation array
	 * @param val - output array
	 */
	public NeuronLayerOutput(double[] act, double[] val)
	{
		this.activation = act;
		this.output = val;
	}
	
	/**
	 * getter method for the activation array/vector
	 * @param i index of element
	 * @return the activation of neuron
	 */
	public double getActivation(int i){return this.activation[i];}
	/**
	 * getter method for the output array/vector
	 * @param i index of element
	 * @return the output of neuron
	 */
	public double getOutput(int i){return this.output[i];}
	/**
	 * getter method for whole output array
	 * @return the output array
	 */
	public double[] getOutputArray(){return this.output;}
}
