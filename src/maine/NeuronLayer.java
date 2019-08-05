package maine;

import activationFunctions.ActivationFunction;

public class NeuronLayer
{
	int inputSize;
	int layerSize;
	Neuron[] neurons;
	
	/**
	 * constructor
	 * @param inputSize size of input it would consume(how many neuron last layer)
	 * @param neuronNum number of neuron in this layer
	 */
	public NeuronLayer(int inputSize, int neuronNum)
	{
		this.inputSize = inputSize;
		this.layerSize = neuronNum;
		this.neurons = new Neuron[neuronNum];
		
		for(int ct=0; ct<neuronNum; ct++)
		{
			this.neurons[ct] = new Neuron(inputSize);
		}
	}
	
	/**
	 * secondary constructor
	 * used when importing from a file
	 * @param s - strings representing the layer (split and trimmed)
	 */
	public NeuronLayer(String[] s)
	{
		int len = s.length;
		this.layerSize = len;
		this.neurons = new Neuron[len];
		
		for(int ct=0; ct<len; ct++)
		{
			this.neurons[ct] = new Neuron(s[ct]);
		}
		
		//messy method tho
		this.inputSize = this.neurons[0].weight.length;
	}
	
	/**
	 * gives 2d array for neuron configuration
	 * @param config the configuration in a 2d array
	 */
	public void setNeuronConfig(double[][] config)
	{
		int len = this.neurons.length;
		for(int ct=0; ct<len; ct++)
		{
			this.neurons[ct].setWeight(config[ct]);
		}
	}
	
	/**
	 * randomize all the axons of this layer of neurons
	 */
	public void randomize()
	{
		int len = this.neurons.length;
		
		for(int ct=0; ct<len; ct++)
		{
			this.neurons[ct].randomize();
		}
	}
	
	/**
	 * method for setting a single activation function
	 * for all neurons in this layer
	 * @param a - ActivationFunction to be inserted
	 */
	public void setWholeLayerActivation(ActivationFunction a)
	{
		int len = this.neurons.length;
		
		for(int ct=0; ct<len; ct++)
		{
			this.neurons[ct].setActivationFunction(a);
		}
	}
	
	/**
	 * sets activation function of whole layer by name
	 * which are specified in ActivationFunctionList
	 * @param s - name of activation function
	 */
	public void setWholeLayerActivation(String s)
	{
		int len = this.neurons.length;
		
		for(int ct=0; ct<len; ct++)
		{
			this.neurons[ct].setActivationFunction(s);
		}
	}
	
	/**
	 * resets the layer and it's neurons
	 */
	public void reset()
	{
		int len = this.neurons.length;
		for(int ct=0; ct<len; ct++)
		{
			this.neurons[ct].reset();
		}
	}
	
	@Deprecated
	/**
	 * feed a neuron layer to calculation
	 * @param n the previous layer's values, in a double[]
	 */
	public void feed(double[] n)
	{
		int len = this.neurons.length;
		for(int ct=0; ct<len; ct++)
		{
			this.neurons[ct].prepareCalculation(n);
			this.neurons[ct].calculate();
		}
	}
	
	/**
	 * the newer method for foward propagating
	 * @param n last layer's output as input or initial input
	 * @return output of this layer as a NeuronLayerOutput object
	 */
	public NeuronLayerOutput feed2(double[] n)
	{
		int len = this.neurons.length;
		//activation and values
		double[] acts = new double[len];
		double[] vals = new double[len];
		
		for(int ct=0; ct<len; ct++)
		{
			//call method to calculate output
			double[] outp = this.neurons[ct].calculate2(n);
			
			
			
			//calculate activation and value for each neuron
			acts[ct] = outp[0];
			vals[ct] = outp[1];
		}
		
		//NeuronLayerOutput will calculate the output from activation
		return new NeuronLayerOutput(acts, vals);
	}
	
	/**
	 * get the value of a specific neuron
	 * @param i index of neuron in layer
	 * @return value of specific neuron
	 */
	public double getNeuronValue(int i)
	{
		return this.neurons[i].getValue();
	}
	
	/**
	 * gets all the values as an array
	 * @return values of all neurons
	 */
	public double[] getNeuronValueArray()
	{
		int len = this.neurons.length;
		double[] res = new double[len];
		
		for(int ct=0; ct<len; ct++)
		{
			res[ct] = this.getNeuronValue(ct);
		}
		
		return res;
	}
	
	/**
	 * backpropagate on this layer and output the error gradient of this layer
	 * @param s error gradient of next layer
	 * @param l learning rate
	 * @return error gradient of this layer
	 */
	@Deprecated
	public double[] backpropagate(double[] s, double l)
	{
		return null;
	}
	
	@Deprecated
	/**
	 * method to get the error gradient when this layer is the 
	 * last(output) layer of the network
	 * @param t target output
	 * @return the error gradient
	 */
	public double[] lastLayerGradient(double[] t)
	{
		int len = t.length;
		
		double[] result = new double[len];
		
		for(int ct=0; ct<len; ct++)
		{
			//(y_n - t_n) * f'(x_n)
			Neuron currentNeuron = this.neurons[ct];
			result[ct] = (currentNeuron.getValue() - t[ct]) * Utili2.sigmoidPrime(currentNeuron.getActivation());
		}
		
		return result;
	}
	
	/**
	 * (newer) method to calculate the gradient
	 * used if this layer is the last layer
	 * @param c correct(target) output
	 * @param nlo output for this layer
	 * (feed as seperate module because need for thread safety)
	 * @return error gradient of this layer
	 */
	public double[] lastLayerGradient2(double[] c, NeuronLayerOutput nlo)
	{
		int len = c.length;
		
		double[] result = new double[len];
		
		//loop for each neuron
		for(int ct=0; ct<len; ct++)
		{
			//f'(x) * (output - target)
			result[ct] = (nlo.getOutput(ct) - c[ct]) * this.neurons[ct].activationPrime(nlo.getActivation(ct));
			//result[ct] = (nlo.getOutput(ct) - c[ct]) * Utili2.sigmoidPrime(nlo.getActivation(ct));
		}
		
		return result;
	}
	
	/**
	 * get the weight matrix of this layer
	 * @return the weight matrix representation of this layer
	 */
	public WeightMatrix toWeightMatrix()
	{
		return new WeightMatrix(this);
	}
	
	@Deprecated
	/**
	 * method to calculate the error gradient when this layer
	 * is not the last(is hidden) layer of the network
	 * @param s the error gradient of the next layer
	 * @param nl the next layer in the network
	 * @return the error gradient of this layer
	 */
	public double[] gradient(double[] s, NeuronLayer nl)
	{
		//the weight matrix
		int thisLen = this.neurons.length;
		int nextLen = nl.neurons.length;
		double[][] weightMatrix = new double[nextLen][thisLen];
		
		//transcribe the weight matrix
		/*
		 * so the matrix will be a nextLen * thisLen matrix
		 * each row is the weight(s) feeding into one neuron in the next layer
		 */
		for(int i=0; i<nextLen; i++)
		{
			for(int j=0; j<thisLen; j++)
			{
				weightMatrix[i][j] = nl.neurons[i].weight[j];
			}
		}
		
		//array to store the results
		double[] result = new double[thisLen];
		
		//iterate each element in the result vector
		for(int j=0; j<thisLen; j++)
		{
			//the sigmoid' of the activation
			double valueSlope = Utili2.sigmoidPrime(this.neurons[j].activation);
			
			//the sum of weight * error
			double sum = 0;
			for(int i=0; i<nextLen; i++)
			{
				sum += weightMatrix[i][j] * s[i];
			}
			
			//put them together
			result[j] = valueSlope * sum;
		}
		
		return result;
	}
	
	/**
	 * the newer(and thread safe) method to calculate error gradient
	 * used if this layer is a hidden(not last) layer
	 * @param d error gradient of next layer
	 * @param wm the WeightMatrix of the next layer
	 * @return error gradient of this layer
	 */
	public double[] gradient2(double[] d, WeightMatrix wm, NeuronLayerOutput nlo)
	{
		//size of this layer
		int len = this.neurons.length;
		//size of next layer
		int nextLen = d.length;
		
		double[] result = new double[len];
		
		//loop for each neuron in this layer
		for(int j=0; j<len; j++)
		{
			double sum = 0;
			for(int l=0; l<nextLen; l++)
			{
				sum += d[l] * wm.getWeight(j, l);
			}
			
			//f'(x) * sum of all(weight * error of next layer)
			result[j] = sum * this.neurons[j].activationPrime(nlo.getActivation(j));
			//result[j] = sum * Utili2.sigmoidPrime(nlo.getActivation(j));
		}
		
		return result;
	}
	
	@Deprecated
	/**
	 * method to update the weights of this layer
	 * @param gradient the error gradient of this layer
	 * @param lastLayerOutput the output of the layer before
	 * @param l learning rate
	 */
	public void updateWeights(double[] gradient, double[] lastLayerOutput, double l)
	{
		//the length of gradient vector
		int len = gradient.length;
		
		for(int j=0; j<len; j++)
		{
			//current neuron
			Neuron currentNeuron = this.neurons[j];
			currentNeuron.updateWeights(gradient[j], lastLayerOutput, l);
		}
	}
	
	/**
	 * newer way to update the weights of neurons
	 * using a WeightUpdateMatrix
	 * @param wum WeightUpdateMatrix calculated for this layer
	 * @param l learning rate
	 */
	public void updateWeights3(WeightUpdateMatrix wum, double l)
	{
		int len = this.neurons.length;
		
		//for each neuron
		for(int ct=0;ct<len; ct++)
		{
			//notation for neuron
			NeuronUpdateNotation notation = wum.exportNeuron(ct);
			//feed into update method
			this.neurons[ct].updateWeights2(notation, l);
		}
	}
	
	/**
	 * writes this layer to a String
	 * so it can be recorded
	 * @return String representation ob this NeuronLayer
	 */
	public String writeToString()
	{
		int len = this.neurons.length;
		
		String result = "{" + System.lineSeparator();
		
		for(int ct=0; ct<len; ct++)
		{
			//concatenate each neuron to the string
			result += (this.neurons[ct].writeToString() + System.lineSeparator());
		}
		
		//end it with end bracket
		result += "}";
		
		return result;
	}
}
