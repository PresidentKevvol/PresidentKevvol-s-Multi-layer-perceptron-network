package tests;

import java.util.Vector;

import maine.GradientList;
import maine.NeuralNetwork;
import maine.NeuralNetworkBuilder;
import maine.NeuronLayer;

public class Test2
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
	
	/*
	 * neural network demonstration with xor gate
	 */
	public static void main(String[] args)
	{
		//dataset and learning rate
		double[][] inputs = {{0,0}, {0,1}, {1,0}, {1,1}};
		double[][] correctOutputs = {{0}, {1}, {1}, {0}};
		double learningRate = 0.3;
		
		//the network we are using
		NeuralNetwork network = buildNetwork();
		
		//loop to train the network
		for(int ct=0; ct<10; ct++)
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
		
		double[] testInp = {0,0};
		double[] result = network.iterate(testInp);
		
		System.out.println("test result: array len:" + result.length + ", value: " + result[0]);
	}
}
