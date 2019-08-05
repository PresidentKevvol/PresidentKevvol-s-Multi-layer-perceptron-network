package trainingObjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * object for reading a file into an array of DataEntry-es
 */
public class FileReading
{
	File f;
	int cor;
	
	/**
	 * creates a FileReading object
	 * @param f - the file to be read
	 * @param i - the target answer index
	 */
	public FileReading(File f, int i)
	{
		this.f = f;
		this.cor = i;
	}
	
	/**
	 * converts a Double Vector to a double array
	 * @param d - the vector
	 * @return the result array
	 */
	private double[] toPrimitive(Vector<Double> d)
	{
		double[] res = new double[d.size()];
		
		for(int ct=0; ct< res.length; ct++)
		{
			res[ct] = d.elementAt(ct);
		}
		
		return res;
	}
	
	public Vector<DataEntry> extract()
	{
		try
		{
			FileReader fr = new FileReader(this.f);
			BufferedReader br = new BufferedReader(fr);
			
			//object for result
			Vector<DataEntry> result = new Vector<DataEntry>();
			
			//vector containing the numbers from lines
			Vector<Double> vect = new Vector<Double>();;
			
			String line;
			//read until file ends
			while((line = br.readLine()) != null)
			{
				if(line.startsWith("{"))
				{
					//do nothing if it's a starting bracket
					continue;
				}
				else if(line.startsWith("}"))
				{
					//put all values into one object when one entry in file finishes
					double[] ay = this.toPrimitive(vect);
					DataEntry et = new DataEntry(this.cor, ay);
					//add to big vector
					result.addElement(et);
					//reset the vector
					vect = new Vector<Double>();
					//System.out.println("FileReading.extract(): one entry ends");
				}
				else//if it's a number(should be)
				{
					//parse into a double and put to array
					double d = Double.parseDouble(line);
					vect.add(d);
				}
			}
			
			br.close();
			
			/*
			//convert vector into array and return
			DataEntry[] res = new DataEntry[result.size()];
			res = result.toArray(res);
			return res;
			*/
			//return the vector as a whole
			return result;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		return null;
	}
}
