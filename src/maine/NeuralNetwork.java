package maine;

import java.util.Vector;

/**
 * class for a whole neural network
 * @author Acer
 *
 */
public class NeuralNetwork
{
	//the size of the initial input array
	int inputSize;
	
	//the network from input side to output side
	NeuronLayer[] network;
	
	/**
	 * build a NeuralNetwork from a builder
	 * @param n
	 */
	public NeuralNetwork(NeuralNetworkBuilder n)
	{
		//NeuronLayer[] layers = n.layers.
		this.network = n.getLayersAsArray();
		this.inputSize = n.getInputSize();
	}
	
	/**
	 * factory method to build a network from string
	 * read from file
	 * @param s - the string representation of a network
	 * @return a NeuralNetwork object
	 */
	public static NeuralNetwork buildFromString(String s)
	{
		//split into individual layers
		String[] layers = s.split("\\{");//"{" is a regex pattern so I have to escape it
		int len = layers.length;
		
		//System.out.println("NeuralNetwork.buildFromString(): split into elements, len: " + len);
		
		NeuralNetworkBuilder nnb = new NeuralNetworkBuilder();
		
		//each layer
		for(int ct=0; ct<len; ct++)
		{
			//if this slice of String don't have an ending curly bracket
			//meaning it is not a vaild neuron layer notation
			if(!(layers[ct].contains("\\}") || layers[ct].contains("}")))
			{
				//System.out.println("NeuralNetwork.buildFromString(): layer element skipped, ct: " + ct);
				System.out.println(layers[ct]);
				//break if it isn't a layer
				//(empty/indent from splitting)
				continue;
			}
			
			//split into individual neurons
			String[] neurons = layers[ct].split("\n");
			//vector storing temporal strings(for checking purposes
			Vector<String> neuronsVect = new Vector<String>();
			
			//for each string
			for(String n : neurons)
			{
				//check if it is a line describing a neuron
				if(n.contains("[") && n.contains("]"))
				{
					neuronsVect.add(n);
				}
			}
			
			//System.out.println("NeuralNetwork.buildFromString(): vector size: " + neuronsVect.size());
			
			//feed into NeuronLayer constructor
			NeuronLayer nl = new NeuronLayer(neuronsVect.toArray(new String[neuronsVect.size()]));
			
			//append
			nnb.appendLayer(nl);
		}
		
		return new NeuralNetwork(nnb);
	}
	
	/**
	 * randomize all configurations
	 */
	public void randomizeAll()
	{
		int len = this.network.length;
		
		for(int ct=0; ct<len; ct++)
		{
			this.network[ct].randomize();
		}
	}
	
	@Deprecated
	/**
	 * method to call when doing a iteration
	 * @param inp input to first layer of neurons
	 * @return the result of the signal
	 */
	public double[] iterate(double[] inp)
	{
		int len = this.network.length;
		
		double[] currentInput;
		NeuronLayer currentLayer;
		double[] lastResult = inp;
		
		//iterate through each layer
		for(int ct=0; ct<len; ct++)
		{
			currentInput = lastResult;
			currentLayer = this.network[ct];
			currentLayer.feed(currentInput);
			lastResult = currentLayer.getNeuronValueArray();
		}
		
		return lastResult;
	}
	
	/**
	 * (newer) method for foward propagation
	 * @param input the input to network
	 * @return the output of each layer(cached for later calculation purposes)
	 */
	public LayerOutputList fowardPropagate(double[] input)
	{
		int len = this.network.length;
		LayerOutputList result = new LayerOutputList(len);
		
		//record input for backpropagation purposes
		result.setInput(input);
		
		//loop revolving variables
		//double[] currentInput;
		double[] lastOutput = input;
		
		for(int ct=0; ct<len; ct++)
		{
			//calculate for layer
			NeuronLayerOutput curOutput = this.network[ct].feed2(lastOutput);
			//put in result object
			result.set(curOutput, ct);
			//setup for next cycle of the loop
			lastOutput = curOutput.getOutputArray();
		}
		
		return result;
	}
	
	/**
	 * do backpropagation on this network
	 * @param r result output (what it outputs)
	 * @param t target/correct output (what it should have output)
	 */
	@Deprecated
	public void backpropagate(double[] r, double[] t)
	{
		int lastLayerLen = t.length;
		//to store the error gradient of the last layer
		double[] lastLayerErrorGradient = new double[lastLayerLen];
		
		//the last layer of the network
		NeuronLayer lastLayer = this.network[this.network.length - 1];
		
		for(int ct=0; ct<lastLayerLen; ct++)
		{
			//(output_lastlayer - target) * sigmoid'(activ_lastlayer)
			lastLayerErrorGradient[ct] = (lastLayer.getNeuronValue(ct) - t[ct]);
		}
	}
	
	@Deprecated
	/**
	 * gets the error gradient of the network,
	 * given the correct output
	 * @param r the correct(desired) output
	 * @return the error gradient
	 */
	public GradientList calculateErrorGradient(double[] r)
	{
		//length(number of layers) of network
		int len = this.network.length;
		//the object to contain the results
		GradientList result = new GradientList(len);
		
		//gradient of last layer, and put it into the last slot of vector
		double[] lastLayerGra = this.network[len - 1].lastLayerGradient(r);
		result.set(len - 1, lastLayerGra);
		
		double[] nextLayerGradient = lastLayerGra;
		
		//iterate back through the layers
		for(int ct=len-2; ct>=0; ct--)
		{
			//calculate for one layer and push it to front of vector
			double[] thisLayer = this.network[ct].gradient(nextLayerGradient, this.network[ct + 1]);
			result.set(ct, thisLayer);
			
			nextLayerGradient = thisLayer;
		}
		
		return result;
	}
	
	/**
	 * the newer way to calculate the error gradient
	 * @param des correct output
	 * @param lis LayerOutputList calculated with input
	 * @return the gradient list(of each layer) to be used for gradient descent
	 */
	public GradientList calculateErrorGradient2(double[] des, LayerOutputList lis)
	{
		int len = this.network.length;
		GradientList result = new GradientList(len);
		
		//the error gradient of the last layer
		double[] lastLayerGradient = this.network[len - 1].lastLayerGradient2(des, lis.get(len - 1));
		
		//put it in the last layer
		result.set(len - 1, lastLayerGradient);
		
		//the gradient of the next (ct + 1 in the loop) layer
		double[] nextLayerGradient = lastLayerGradient;
		
		//iterate back through the layers
		for(int ct=len-2; ct>=0; ct--)
		{
			//next layer's weight matrix
			WeightMatrix nextLayerWeightMatrix = this.network[ct + 1].toWeightMatrix();
			//this layer's output
			NeuronLayerOutput thisLayerOutput = lis.get(ct);
			
			//calculate for one layer and push it to front of vector
			double[] thisLayer = this.network[ct].gradient2(nextLayerGradient, nextLayerWeightMatrix, thisLayerOutput);
			result.set(ct, thisLayer);
			
			nextLayerGradient = thisLayer;
		}
		
		return result;
	}
	
	/**
	 * update the weight based on the given error gradient
	 * @param grad error gradient
	 * @param l learning rate
	 * @param input the input to this network
	 */
	@Deprecated
	public void updateWeights(GradientList grad, double l, double[] input)
	{
		int len = this.network.length;
		
		//iterate in each layer until the second layer
		for(int ct=len-1; ct>=1; ct--)
		{
			NeuronLayer currentLayer = this.network[ct];
			double[] currentGradient = grad.elementAt(ct);
			NeuronLayer layerBefore = this.network[ct - 1];
			
			int currentLayerLen = currentLayer.neurons.length;
			
			//each neuron in a layer
			//in the loop, j will be the 'index of the current neuron'
			for(int j=0; j<currentLayerLen; j++)
			{
				Neuron currentNeuron = currentLayer.neurons[j];
				int currentNeuronLen = currentNeuron.weight.length;
				
				//iterate through each synapses(axons)
				//i will be the 'index of the current synapse'
				for(int i=0; i<currentNeuronLen; i++)
				{
					//error gradient of this neuron * input to this axon from layer b4 * learning rate
					double deltaWeight = currentGradient[j] * layerBefore.getNeuronValue(i) * l;
					//subtract change from weight
					currentNeuron.weight[i] -= deltaWeight;
				}
			}
		}
		
		//process the first layer after the big loop
		NeuronLayer firstLayer = this.network[0];
		int firstLayerLen = firstLayer.neurons.length;
		double[] firstLayerGradient = grad.firstElement();
		
		//iterate through each neuron
		//in the loop, j will be the 'index of the current neuron'
		for(int j=0; j<firstLayerLen; j++)
		{
			Neuron currentNeuron = firstLayer.neurons[j];
			int currentNeuronLen = currentNeuron.weight.length;
			
			//iterate through each synapses(axons)
			//i will be the 'index of the current synapse'
			for(int i=0; i<currentNeuronLen; i++)
			{
				//error gradient of this neuron * input to this axon from layer b4 * learning rate
				double deltaWeight = firstLayerGradient[j] * input[i] * l;
				//subtract change from weight
				currentNeuron.weight[i] -= deltaWeight;
			}
		}
	}
	
	@Deprecated
	/**
	 * the better way to update the weight based on the given error gradient
	 * @param grad error gradient
	 * @param l learning rate
	 * @param input the input to this network
	 */
	public void updateWeights2(GradientList grad, double l, double[] input)
	{
		int len = this.network.length;
		
		//from the last layer back to the second
		for(int ct=len-1; ct>=1; ct--)
		{
			//current layer
			NeuronLayer currentLayer = this.network[ct];
			//output of last layer
			double[] lastLayerOutput = this.network[ct - 1].getNeuronValueArray();
			
			currentLayer.updateWeights(grad.elementAt(ct), lastLayerOutput, l);
		}
		
		//then the first layer
		//it's 'last layer' will be the inputs
		NeuronLayer firstLayer = this.network[0];
		firstLayer.updateWeights(grad.elementAt(0), input, l);
	}
	
	/**
	 * the newer way to update the weights
	 * using a WeightUpdateMatrixCollection
	 * @param wumc the WeightUpdateMatrixCollection calculated from backpropagation
	 * @param l learning rate
	 */
	public void updateWeights3(WeightUpdateMatrixCollection wumc, double l)
	{
		//length of layers of network
		int len = this.network.length;
		
		//loop to update each layer
		for(int ct=0; ct<len; ct++)
		{
			this.network[ct].updateWeights3(wumc.get(ct), l);
		}
	}
	
	/**
	 * method to turn a network into a string
	 * so it can be written into a text file and recorded
	 * @return the string representation of the neural network
	 */
	public String writeToString()
	{
		int len = this.network.length;
		String result = "";
		
		for(int ct=0; ct<len; ct++)
		{
			//write each layer
			result += (this.network[ct].writeToString() + System.lineSeparator());
		}
		
		return result;
	}
}
