package maine;

/**
 * a weight matrix constructed from a neuron layer
 * to represent the weights
 * @author Acer
 *
 */
public class WeightMatrix extends WeightMatrixBase
{
	/**
	 * sole constructor
	 * takes in a NeuronLayer and extract weight and bias values from each neuron
	 * @param nl
	 */
	public WeightMatrix(NeuronLayer nl)
	{
		super(nl.inputSize, nl.layerSize);
		
		//number of neurons in this layer
		int numNeuron = nl.neurons.length;
		//number of neurons in the layer feeding into it
		int numInput = nl.neurons[0].weight.length;
		
		this.inputSize = numInput;
		this.outputSize = numNeuron;
		this.weightMatrix = new double[numNeuron][numInput];
		this.biasVector = new double[numNeuron];
		
		//iterate through each neuron
		for(int i=0; i<numNeuron; i++)
		{
			//weight and bias
			for(int j=0; j<numInput; j++)
			{
				this.weightMatrix[i][j] = nl.neurons[i].weight[j];
			}
			this.biasVector[i] = nl.neurons[i].bias;
		}
	}
	
	/**
	 * gets the weight value of a specific synapse
	 * @param i index of the neuron it gets fed from
	 * @param j index of the neuron it is feeding into
	 * @return weight value
	 */
	public double getWeight(int i, int j)
	{
		return this.weightMatrix[j][i];
	}
	
	/**
	 * gets a bias of a neuron
	 * @param i index of neuron
	 * @return bias of neuron
	 */
	public double getBias(int i)
	{
		return this.biasVector[i];
	}
	
	/**
	 * getter method for the size of input
	 * @return number of input neurons
	 */
	public int getInputSize(){return this.inputSize;}
	
	/**
	 * getter method for the size of feeding layer
	 * @return number of neurons in the feeding layer
	 */
	public int getLayerSize(){return this.outputSize;}
}
