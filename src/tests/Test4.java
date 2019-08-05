package tests;

import maine.LayerOutputList;
import maine.NeuralNetwork;
import maine.NeuralNetworkBuilder;
import activationFunctions.Arctan;
import maine.*;

public class Test4
{
	public static NeuralNetwork buildNetwork()
	{
		//create builder
		NeuralNetworkBuilder nnb = new NeuralNetworkBuilder();
		
		//activation function for the network
		Arctan arctan = new Arctan();
		
		//build the layers and give them random values as well as define the activation function
		NeuronLayer layer1 = new NeuronLayer(2, 4);
		layer1.randomize();
		layer1.setWholeLayerActivation(arctan);
		
		NeuronLayer layer2 = new NeuronLayer(4, 4);
		layer2.randomize();
		layer2.setWholeLayerActivation(arctan);
		
		//layer 3 is the output layer
		NeuronLayer layer3 = new NeuronLayer(4, 1);
		layer3.randomize();
		layer3.setWholeLayerActivation(arctan);
		
		//append layers
		nnb.appendLayer(layer1);
		nnb.appendLayer(layer2);
		nnb.appendLayer(layer3);
		
		//build the object and return
		NeuralNetwork network = new NeuralNetwork(nnb);
		return network;
	}
	
	public static void main(String[] args)
	{
		//create network
		NeuralNetwork network = buildNetwork();
		
		double learningRate = 0.1;
		
		//output modeled after xor gate function
		double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		double[][] outputs = {{-1}, {1}, {1}, {-1}};
		
		for(int ct=0; ct<3000; ct++)
		{
			//the current index of training data that we will use
			//array index (0~3)
			int curInd = (int)(Math.random()*4);
			
			//foward propogate to get outputs
			LayerOutputList output = network.fowardPropagate(inputs[curInd]);
			//calculate error gradient from output
			GradientList gList = network.calculateErrorGradient2(outputs[curInd], output);
			
			//System.out.println("Test4.main() gList.size(): " + gList.size());
			//System.out.println("" + gList.intoStringTest());
			
			//create gradient pair
			GradientPair gradientPair = new GradientPair(output, gList);
			//execute gradient descent
			WeightUpdateMatrixCollection wumc = gradientPair.gradientDescent();
			
			//update the weights
			network.updateWeights3(wumc, learningRate);
		}
		
		//give test case to network
		double[] test = {0, 1};
		double[] test2 = {0, 0};
		LayerOutputList resultList = network.fowardPropagate(test);
		LayerOutputList resultList2 = network.fowardPropagate(test2);
		
		//results!
		double[] result = resultList.get(2).getOutputArray();
		double[] result2 = resultList2.get(2).getOutputArray();
		
		//System.out.println("result length: " + result.length);
		System.out.println("Training result: " + result[0]);
		System.out.println("Training result: " + result2[0]);
	}
}
