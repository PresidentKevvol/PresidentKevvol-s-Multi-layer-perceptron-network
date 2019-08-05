package maine;

import java.util.Vector;

/**
 * a list(vector) of double[]'s to store the error gradient in a neural network
 * @author Acer
 *
 */
public class GradientList extends Vector<double[]>
{
	/**
	 * usual/default constructor
	 * @param numLayers number of layers
	 */
	public GradientList(int numLayers)
	{
		super(numLayers);
		
		for(int ct=0; ct<numLayers; ct++)
		{
			super.addElement(null);
		}
	}
	
	/**
	 * turn the whole error gradient list into a string
	 * @return the list in the form of a string
	 */
	public String intoStringTest()
	{
		String result = "Gradient list:\n";
		
		for(double[] d : this)
		{
			//debug
			//System.out.println("GradientList.intoStringTest(): outer loop once");
			
			String currentRow = "{";
			
			for(double dd: d)
			{
				currentRow += (dd + ", ");
			}
			
			currentRow = currentRow.substring(0, currentRow.length() - 2);
			
			currentRow += "}";
			
			result += (currentRow + "\n");
		}
		
		result = result.substring(0, result.length() - 1);
		
		return result;
	}
}
