package tests;

import maine.NeuralNetwork;
import maine.NeuralNetworkBuilder;
import maine.NeuronLayer;

public class Test1
{
	public static void main(String[] args)
	{
		NeuralNetworkBuilder builder = new NeuralNetworkBuilder();
		
		NeuronLayer firstLayer = new NeuronLayer(2, 3);
		NeuronLayer secondLayer = new NeuronLayer(3, 1);
		
		//first layer
		double[][] firstConfig = {{0.8,0.2}, {0.4,0.9}, {0.3, 0.5}};
		firstLayer.setNeuronConfig(firstConfig);
		
		//second layer
		double[][] secondConfig = {{0.3, 0.5, 0.9}};
		secondLayer.setNeuronConfig(secondConfig);
		
		builder.appendLayer(firstLayer);
		builder.appendLayer(secondLayer);
		builder.setInputSize(2);
		
		NeuralNetwork network = new NeuralNetwork(builder);
		
		//input and result
		double[] inp = {1.0, 1.0};
		double[] res = network.iterate(inp);
		
		System.out.println("test result: " + res[0]);
	}
}
