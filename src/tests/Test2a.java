package tests;

import maine.GradientList;
import maine.NeuralNetwork;
import maine.NeuralNetworkBuilder;
import maine.NeuronLayer;

public class Test2a
{
	public static NeuralNetwork buildNetwork()
	{
		NeuralNetworkBuilder builder = new NeuralNetworkBuilder();
		
		NeuronLayer firstLayer = new NeuronLayer(2, 3);
		NeuronLayer secondLayer = new NeuronLayer(3, 3);
		NeuronLayer thirdLayer = new NeuronLayer(3, 1);
		
		//first layer
		double[][] firstConfig = {{0,0}, {0,0}, {0, 0}};
		firstLayer.setNeuronConfig(firstConfig);
		
		//second layer
		double[][] secondConfig = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		secondLayer.setNeuronConfig(secondConfig);
		
		//third layer
		double[][] thirdConfig = {{0, 0, 0}};
		thirdLayer.setNeuronConfig(thirdConfig);
		
		builder.appendLayer(firstLayer);
		builder.appendLayer(secondLayer);
		builder.appendLayer(thirdLayer);
		builder.setInputSize(2);
		
		//the result network
		NeuralNetwork network = new NeuralNetwork(builder);
		
		return network;
	}
	
	public static void main(String[] args)
	{
		//dataset and learning rate
		double[][] inputs = {{0,0}, {0,1}, {1,0}, {1,1}};
		double[][] correctOutputs = {{0}, {1}, {1}, {0}};
		double learningRate = 0.05;
				
		//the network we are using
		NeuralNetwork network = buildNetwork();
				
		//loop to train the network
		for(int ct=0; ct<1000; ct++)
		{
			int ran = (int)Math.floor(Math.random() * 4);
			//System.out.println("Test2.main(): value of ran: " + ran);
			double[] input = inputs[ran];
			double[] correctOutput = correctOutputs[ran];
					
			double[] output = network.iterate(input);
			System.out.println("Test2.main(): value of output[0]: " + output[0]);
			System.out.println("Test2.main(): value of correctOutput[0]: " + correctOutput[0]);
			System.out.println("Test2.main(): difference: " + (output[0] - correctOutput[0]));
					
			GradientList gradient = network.calculateErrorGradient(correctOutput);
					
			System.out.println("Test2.main(): Gradient List values:\n" + gradient.intoStringTest() + "\n");
					
			network.updateWeights2(gradient, learningRate, input);
		}
				
		double[] testInp1 = {0,0};
		double[] result1 = network.iterate(testInp1);
		
		double[] testInp2 = {1,0};
		double[] result2 = network.iterate(testInp2);
				
		System.out.println("test result1: array len:" + result1.length + ", value: " + result1[0]);
		System.out.println("test result2: array len:" + result2.length + ", value: " + result2[0]);
	}
}
