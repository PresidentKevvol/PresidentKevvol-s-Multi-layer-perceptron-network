package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import maine.NeuralNetwork;

/**
 * test for reading a neural network from string
 */
public class Test6
{
	public static void main(String[] args)
	{
		File f = new File("apa.txt");
		try
		{
			FileReader fr = new FileReader(f);
			//FileInputStream fis = new FileInputStream(f);
			//length of file
			int lengt = (int)f.length();
			char[] read = new char[lengt];
			fr.read(read);
			
			String s = new String(read);
			
			NeuralNetwork net1 = NeuralNetwork.buildFromString(s);
			
			//close the reader
			fr.close();
			
			FileWriter fw = new FileWriter("sava.txt");
			fw.write(net1.writeToString());
			
			fw.close();
		}
		catch(IOException ioe)
		{
			System.out.println("Test6.main(): exception: " + ioe.getLocalizedMessage());
			ioe.printStackTrace();
		}
	}
}
