package maine;

public class WeightUpdateMatrix extends WeightMatrixBase
{
	/**
	 * default constructor
	 * @param inputSize size of last layer
	 * @param layerSize size of this layer
	 */
	public WeightUpdateMatrix(int inputSize, int layerSize)
	{
		super(inputSize, layerSize);
	}
	
	/**
	 * getter method for a weight of a synapse
	 * @param i index of neuron fed from
	 * @param j index of neuron feeding into
	 * @return value(weight) of synapse
	 */
	public double getWeight(int i, int j){return this.weightMatrix[j][i];}
	/**
	 * getter method for a bias
	 * @param j index of neuron
	 * @return value of bias of that neuron
	 */
	public double getBias(int j){return this.biasVector[j];}
	
	/**
	 * setter method for a weight
	 * @param i index of neuron fed from
	 * @param j index of neuron feeding to
	 * @param d weight value to be set
	 */
	public void setWeight(int i, int j, double d){this.weightMatrix[j][i] = d;}
	/**
	 * setter method for a bias
	 * @param j index of neuron
	 * @param d bias value to be set
	 */
	public void setBias(int j, double d){this.biasVector[j] = d;}
	
	/**
	 * method for getting values to update neuron
	 * @param j index of neuron
	 * @return NeuronUpdatingNotation object for updating neuron
	 */
	public NeuronUpdateNotation exportNeuron(int j)
	{
		return new NeuronUpdateNotation(this.weightMatrix[j], this.biasVector[j]);
	}
}
