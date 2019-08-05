package maine;

/**
 * base class for a weight matrix
 * @author Acer
 *
 */
public class WeightMatrixBase
{
	int inputSize;
	int outputSize;
	double[][] weightMatrix;
	double[] biasVector;
	
	/**
	 * default constructor
	 * @param inputSize size of the layer before
	 * @param layerSize size of this layer
	 */
	protected WeightMatrixBase(int inputSize, int layerSize)
	{
		this.inputSize = inputSize;
		this.outputSize = layerSize;
		this.weightMatrix = new double[layerSize][inputSize];
		this.biasVector = new double[layerSize];
	}
}
