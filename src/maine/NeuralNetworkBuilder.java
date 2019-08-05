package maine;

import java.util.Vector;

/**
 * builder class to build a neural network
 * @author Acer
 *
 */
public class NeuralNetworkBuilder
{
	int inputSize;
	Vector<NeuronLayer> layers;
	
	/**
	 * constructor to initiate a neural network
	 */
	public NeuralNetworkBuilder()
	{
		this.layers = new Vector<NeuronLayer>();
	}
	
	/**
	 * use to build the network at beginning
	 * @param n the layer to append
	 */
	public void appendLayer(NeuronLayer n)
	{
		layers.addElement(n);
	}
	
	/**
	 * setter method of inputSize
	 * @param i input size
	 */
	public void setInputSize(int i)
	{
		this.inputSize = i;
	}
	
	/**
	 * method to get the neuron layers as an array
	 * can be used in the constructor of NeuralNetwork
	 * @return the neuron layers in an array
	 */
	public NeuronLayer[] getLayersAsArray()
	{
		NeuronLayer[] layersArray = new NeuronLayer[this.layers.size()];
		layersArray = this.layers.toArray(layersArray);
		return layersArray;
	}
	
	/**
	 * getter method for the inputSize
	 * can be used in constructor of NeuralNetwork
	 * @return input size recorded
	 */
	public int getInputSize()
	{
		return this.inputSize;
	}
}
