package tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import maine.NeuralNetwork;
import maine.NeuralNetworkBuilder;
import maine.NeuronLayer;

/**
 * test for neuron writing to file funtionalities
 * 
 */
public class Test5
{
	public static void main(String[] args)
	{
		//build and randomize the network
		NeuralNetworkBuilder build = new NeuralNetworkBuilder();
		NeuronLayer layer1 = new NeuronLayer(2, 4);
		layer1.randomize();
		
		NeuronLayer layer2 = new NeuronLayer(4, 4);
		layer2.randomize();
		
		NeuronLayer layer3 = new NeuronLayer(4, 1);
		layer3.randomize();
		
		build.appendLayer(layer1);
		build.appendLayer(layer2);
		build.appendLayer(layer3);
		
		NeuralNetwork nn = new NeuralNetwork(build);
		
		//print test
		//System.out.println(nn.writeToString());
		
		//file writing test
		File f = new File("apa.txt");
		try
		{
			FileWriter fw = new FileWriter(f);
			fw.write(nn.writeToString());
			fw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
