package maine;

/**
 * class used for backpropagation
 * calculates the change needed to apply on each weight
 * @author Acer
 *
 */
public class GradientPair
{
	LayerOutputList outputList;
	GradientList gradientList;
	
	/**
	 * standard constructor
	 * @param lol LayerOutputList resulted from foward propagation
	 * @param gl GradientList result from calculation
	 */
	public GradientPair(LayerOutputList lol, GradientList gl)
	{
		this.outputList = lol;
		this.gradientList = gl;
	}
	
	/**
	 * create a collection of WeightUpdateMatrix's from data
	 * @return WeightUpdateMatrixCollection object containing information to tune the neural network
	 */
	public WeightUpdateMatrixCollection gradientDescent()
	{
		//number of layers
		int numLayers = this.gradientList.size();
		WeightUpdateMatrixCollection result = new WeightUpdateMatrixCollection(numLayers);
		
		//first layer calculated with inputs
		WeightUpdateMatrix firstLayer = this.calculateUpdateMatrix(this.outputList.inputs, this.gradientList.firstElement());
		result.set(firstLayer, 0);
		
		//calculate weight update for each layer
		for(int ct=1; ct<numLayers; ct++)
		{
			//debug
			//System.out.println("GradientPair.gradientDescent(): weight loop: value of ct: " + ct);
			
			//last layer's output and this layer's gradient
			double[] lastOutput = this.outputList.get(ct - 1).getOutputArray();
			double[] thisGradient = this.gradientList.elementAt(ct);
			
			WeightUpdateMatrix currentMatrix = this.calculateUpdateMatrix(lastOutput, thisGradient);
			result.set(currentMatrix, ct);
		}
		
		return result;
	}
	
	/**
	 * calculates the change for weight for one layer
	 * @param lastOutput last layer's output
	 * @param thisGradient this layer's error gradient
	 * @return WeightUpdateMatrix as result
	 */
	private WeightUpdateMatrix calculateUpdateMatrix(double[] lastOutput, double[] thisGradient)
	{
		int lastlen = lastOutput.length;
		int thislen = thisGradient.length;
		WeightUpdateMatrix result = new WeightUpdateMatrix(lastlen, thislen);
		
		for(int j=0; j<thislen; j++)
		{
			for(int i=0; i<lastlen; i++)
			{
				//calculate delta weight
				double value = lastOutput[i] * thisGradient[j];
				result.setWeight(i, j, value);
			}
			
			//calculate delta bias
			double bias = 1.0 * thisGradient[j];
			result.setBias(j, bias);
		}
		
		return result;
	}
}
