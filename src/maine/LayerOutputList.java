package maine;

public class LayerOutputList
{
	NeuronLayerOutput[] layerOutputs;
	double[] inputs;
	
	/**
	 * sole constructor
	 * @param i the number of layers in list
	 */
	public LayerOutputList(int i)
	{
		this.layerOutputs = new NeuronLayerOutput[i];
	}
	
	/**
	 * setter method for output list
	 * @param nlo NeuronLayerOutput object to be inserted
	 * @param i index
	 */
	public void set(NeuronLayerOutput nlo, int i){this.layerOutputs[i] = nlo;}
	/**
	 * getter method for output list
	 * @param i index
	 * @return NeuronLayerOutput in index
	 */
	public NeuronLayerOutput get(int i){return this.layerOutputs[i];}
	/**
	 * convinient method to get just the output array/output vector
	 * @return the output array from the neural network
	 */
	public double[] getOutput(){return this.layerOutputs[this.layerOutputs.length - 1].getOutputArray();}
	/**
	 * method for setting(recording) the input
	 * @param d the input array
	 */
	public void setInput(double[] d){this.inputs = d;}
}
