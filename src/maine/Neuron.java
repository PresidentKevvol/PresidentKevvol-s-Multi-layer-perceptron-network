package maine;

import java.lang.reflect.Array;
import java.util.Arrays;

import activationFunctions.ActivationFunction;
import activationFunctions.ActivationFunctionList;

/**
 * class for a neuron
 * @author Acer
 *
 */
public class Neuron
{
	//the before network that feeds into this neuron
	double[] input;
	
	//the weight of each input neuron
	double[] weight;
	
	//the bias of the neuron
	double bias;
	
	//the activation(calculated sum of inputs) of the neuron
	double activation;
	
	//the value( activation_function(activation) ) of the neuron
	double value;
	
	//keep track of this neuron has been activated or not
	boolean valueFired;
	
	//the activation function to be used if specified
	ActivationFunction activationFunction;
	
	//the name of the activation function, if specified
	String activationFunctionName;
	
	/**
	 * usual constructor
	 * @param lastLayerSize the size of the last layer
	 */
	public Neuron(int lastLayerSize)
	{
		weight = new double[lastLayerSize];
		bias = 0;
	}
	
	/**
	 * secondary constructor
	 * used when reading from file
	 * @param s - String representing this Neuron
	 */
	public Neuron(String s)
	{
		//cut off from the brackets
		int start = s.indexOf("[");
		int end = s.indexOf("]");
		s = s.substring(start + 1, end).trim();
		
		//result array
		String[] res = s.split(" ");
		
		//if containing an (optional) activation function at last element
		if(res[res.length - 1].startsWith("\""))
		{
			//set it to the corresponding activation function
			String actName = res[res.length - 1].replaceAll("\"", "");
			this.setActivationFunction(actName);
			//remove the last element of array for later processing
			res = Arrays.copyOfRange(res, 0, res.length - 1);
		}
		
		//length - 2 synapses (last 2 elements are "b" and bias)
		int weightLen = res.length - 2;
		this.weight = new double[weightLen];
		
		//record all the weight
		for(int ct=0; ct<weightLen; ct++)
		{
			this.weight[ct] = Double.parseDouble(res[ct]);
		}
		
		//last element is bias
		this.bias = Double.parseDouble(res[res.length - 1]);
	}
	
	/**
	 * randomizes the weight of axons feeding into this neuron
	 */
	public void randomize()
	{
		for(int ct=0; ct<this.weight.length; ct++)
		{
			//give axon a weight of range [-100, 100)
			this.weight[ct] = Math.random() * 600 - 300;
		}
		
		//bias too
		this.bias =  Math.random() * 600 - 300;
	}
	
	/**
	 * setter of activation function
	 * @param a the implementation of the activation function's interface
	 */
	public void setActivationFunction(ActivationFunction a)
	{
		this.activationFunction = a;
	}
	
	/**
	 * alternate method for setting the activation function<br>
	 * use the activation function from ActivationFunctionList according to the name given
	 * @param s - name of activation function
	 */
	public void setActivationFunction(String s)
	{
		if(ActivationFunctionList.list.containsKey(s))
		{
			this.activationFunctionName = s;
			this.activationFunction = ActivationFunctionList.list.get(s);
		}
	}
	
	/**
	 * set the weight of axons
	 * @param d the weights in a double[]
	 */
	public void setWeight(double[] d)
	{
		this.weight = d;
	}
	
	/**
	 * the setter method of the bias
	 * @param d the new bias
	 */
	public void setBias(double d)
	{
		this.bias = d;
	}
	
	/**
	 * starts the neuron's process
	 * @param n the layer feeding into it
	 */
	public void prepareCalculation(double[] n)
	{
		this.input = n;
	}
	
	@Deprecated
	/**
	 * the function that calculate the value of the neuron
	 */
	public void calculate()
	{
		int numInputs = input.length;
		
		//add up the input*weight
		double total = 0;
		for(int ct=0; ct<numInputs; ct++)
		{
			double curWeight = this.input[ct] * this.weight[ct];
			total += curWeight;
		}
		
		//add the bias in
		total += this.bias;
		
		//record activation value
		this.activation = total;
		//pass value into activation function
		if(this.activationFunction == null)
		{
			this.value = Utili2.sigmoid(total);
		}
		else//if an activation function is specified
		{
			this.value = this.activationFunction.f(total);
		}
		//finished
		this.valueFired = true;
		
		//System.out.println("Neuron.calculate(): value of value: " + this.value);
	}
	
	/**
	 * gets the value of the neuron
	 * @return the value if calculation is done, else 0
	 */
	public double getValue()
	{
		if(valueFired)
		{
			return value;
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * getter method of the activation
	 * aka sum of all input from last layer times weights
	 * @return activation
	 */
	public double getActivation()
	{
		return this.activation;
	}
	
	/**
	 * resets the calculation for next load
	 */
	public void reset()
	{
		this.input = null;
		this.valueFired = false;
	}
	
	/**
	 * method for calculating the value after activation
	 * @param x - the input, x
	 * @return the output
	 */
	public double activation(double x)
	{
		if(this.activationFunction == null)
		{
			return Utili2.sigmoid(x);
		}
		else
		{
			return this.activationFunction.f(x);
		}
	}
	
	/**
	 * method for getting the first derivative of the activation
	 * (slope of the activation in the activation function)
	 * to be used for gradient descent
	 * @param x - the input, x
	 * @return output of f'(x)
	 */
	public double activationPrime(double x)
	{
		if(this.activationFunction == null)
		{
			return Utili2.sigmoidPrime(x);
		}
		else
		{
			return this.activationFunction.fPrime(x);
		}
	}
	
	@Deprecated
	/**
	 * method to update the weights of this neuron
	 * @param partialW the error gradient of this neuron/node
	 * @param lastLayer the output of layer before
	 * @param l learning rate
	 */
	public void updateWeights(double partialW, double[] lastLayer, double l)
	{
		int len = this.weight.length;
		
		//first update all the synapses
		for(int i=0; i<len; i++)
		{
			//error gradient * input of layer b4 * learning rate
			double deltaWeight = partialW * lastLayer[i] * l;
			this.weight[i] -= deltaWeight;
		}
		
		//then the bias
		//treat the bias as a synapse and have a constant input of 1
		double deltaBias = partialW * 1.0 * l;
		this.bias -= deltaBias;
	}
	
	/**
	 * new method for calculating output
	 * @return the activation(sum of all input*weight + bias) of neuron
	 */
	public double[] calculate2(double[] d)
	{
		//the total sum will be the activation
		double sum = 0;
		
		int len = d.length;
		//sum of all weights * input
		for(int ct=0; ct<len; ct++)
		{
			sum += d[ct] * weight[ct];
		}
		
		//add bias
		sum += bias;
		
		//the value after the activation function
		double value = this.activation(sum);
		
		//result
		double[] result = {sum, value};
		
		return result;
	}
	
	/**
	 * the newer way to update weights of a neuron
	 * @param nun NeuronUpdateNotation containing the delta weights
	 * @param l learning rate
	 */
	public void updateWeights2(NeuronUpdateNotation nun, double l)
	{
		int len = this.weight.length;
		
		//for each synapse
		for(int ct=0; ct< len; ct++)
		{
			//testing purposes
			if(Double.isNaN(nun.getWeight(ct)))
			{
				System.out.println("Neuron.updateWeights2(): delta-weight result NaN, ct: " + ct);
				Thread.dumpStack();
			}
			
			//change weight by update * learning rate
			this.weight[ct] -= (nun.getWeight(ct) * l);
		}
		
		//testing purposes
		if(Double.isNaN(nun.getBias()))
		{
			System.out.println("Neuron.updateWeights2(): delta-bias result NaN;");
			Thread.dumpStack();
		}
		
		//and the bias too
		this.bias -= (nun.getBias() * l);
		
		/*
		//testing purposes
		if(this.bias == Double.NaN)
		{
			System.out.println("Neuron.updateWeights2(): bias result NaN;");
			Thread.dumpStack();
		}
		*/
	}
	
	/**
	 * convert a neuron into a string
	 * so it could be written to file and recorded
	 * @return java.String representation of the neuron
	 */
	public String writeToString()
	{
		int len = this.weight.length;
		
		String result = "[";
		
		//write each weight with space between
		for(int ct=0; ct<len; ct++)
		{
			result += (" " + this.weight[ct]);
		}
		
		//the bias as well
		result += (" b " + this.bias +  " ");
		
		//and the activation function, if specified
		if(this.activationFunctionName != null)
		{
			result += ("\"" + this.activationFunctionName + "\" ");
		}
		
		result += "]";
		
		return result;
	}
}
