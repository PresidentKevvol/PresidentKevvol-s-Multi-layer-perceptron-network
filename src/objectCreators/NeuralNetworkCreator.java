package objectCreators;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import maine.NeuralNetwork;
import maine.NeuralNetworkBuilder;
import maine.NeuronLayer;
import trainingObjects.StaticValues;

/**
 * fieldless class that creates neural networks
 * to be used for training
 */
public class NeuralNetworkCreator
{
	/**
	 * generates a randomized 25, 40, 1 perceptron network
	 * @return generated network
	 */
	public NeuralNetwork generateRandomize()
	{
		NeuralNetworkBuilder nnb = new NeuralNetworkBuilder();
		
		NeuronLayer layer1 = new NeuronLayer(StaticValues.InputArraySize, 40);
		layer1.randomize();
		layer1.setWholeLayerActivation("ReLU_leaky");
		nnb.appendLayer(layer1);
		
		NeuronLayer layer2 = new NeuronLayer(40, 2);
		layer2.randomize();
		layer2.setWholeLayerActivation("Arctan");
		nnb.appendLayer(layer2);
		
		return new NeuralNetwork(nnb);
	}
	
	/**
	 * creates a neural network by reading it from a file
	 * @param s - name of file
	 * @return NeuralNetwork defined in file
	 */
	public NeuralNetwork createFromFile(String s)
	{
		File f = new File(s);
		
		try
		{
			FileReader fr = new FileReader(f);

			int len = (int)f.length();
			char[] cont = new char[len];

			fr.read(cont);

			String contents = new String(cont);
			
			fr.close();

			return NeuralNetwork.buildFromString(contents);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return null;
		}
	}
	
	public void writeToFile(NeuralNetwork nn, String s)
	{
		//convert neural network to string
		String config = nn.writeToString();
		//create file object
		File f = new File(s);
		try
		{
			//write it to the file
			FileWriter fw = new FileWriter(f);
			fw.write(config);
			fw.flush();
			//close file
			fw.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
