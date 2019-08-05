package tests;

import maine.LayerOutputList;
import maine.NeuralNetwork;
import maine.NeuralNetworkBuilder;
import maine.NeuronLayer;

public class Test3
{
	public static NeuralNetwork buildNetwork()
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
		
		//the result network
		NeuralNetwork network = new NeuralNetwork(builder);
		
		return network;
	}
	
	public static void main(String[] args)
	{
		NeuralNetwork nn = buildNetwork();
		
		double[] inp = {1, 1};
		
		LayerOutputList lol = nn.fowardPropagate(inp);
		
		System.out.println(lol.get(1).getOutput(0));
	}
}
