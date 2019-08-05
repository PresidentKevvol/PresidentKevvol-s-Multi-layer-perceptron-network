package maine;

/**
 * class used to store update information for weights in a neural network
 * @author Acer
 *
 */
public class WeightUpdateMatrixCollection
{
	WeightUpdateMatrix[] layers;
	
	/**
	 * standard constructor
	 * define number of layers the target neural network have
	 * @param i number of layers
	 */
	public WeightUpdateMatrixCollection(int i)
	{
		this.layers = new WeightUpdateMatrix[i];
	}
	
	/**
	 * setter method for a layer
	 * @param wm weight matrix to be inserted
	 * @param i index number of layer
	 */
	public void set(WeightUpdateMatrix wm, int i){this.layers[i] = wm;}
	
	/**
	 * getter method for a single layer
	 * @param i the index of layer
	 * @return the WeightUpdateMatrix of that index
	 */
	public WeightUpdateMatrix get(int i){return this.layers[i];}
}
