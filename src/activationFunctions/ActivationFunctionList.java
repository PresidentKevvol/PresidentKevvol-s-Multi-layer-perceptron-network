package activationFunctions;

import java.util.HashMap;

/**
 * the list of activation functions<br>
 * their names are written and read in neural network files
 */
public class ActivationFunctionList
{
	public static HashMap<String, ActivationFunction> list;
	
	//static initializer block containing the definations of
	//the activation functions
	static
	{
		list = new HashMap<String, ActivationFunction>();
		
		list.put("Arctan", new Arctan());
		list.put("Tanh", new Tanh());
		list.put("ReLU", new ReLU());
		list.put("ReLU_leaky", new ReLU_leaky());
	}
}
