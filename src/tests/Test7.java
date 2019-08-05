package tests;

import maine.NeuronLayer;

/**
 * test for read from string of a single neuron layer
 */
public class Test7
{
	public static void main(String[] args)
	{
		String[] s = 
			{"[ 21.334215486223457 76.6068117951292 b 36.373021269252746 ]"
			,"[ 7.7521916433095726 182.83401461288088 b -1.6699139786211106 ]"
			,"[ -38.67655689415889 -11.08454118528158 b -174.1974634560579 ]"
			,"[ -45.58649876249024 42.85609142759435 b -139.65630688632777 ]"
			};
		
		NeuronLayer nl = new NeuronLayer(s);
		
		System.out.println(nl.writeToString());
	}
}
