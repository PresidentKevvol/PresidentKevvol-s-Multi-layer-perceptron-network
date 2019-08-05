package utili;

import java.util.HashMap;

public class NotePitch
{
	HashMap<Integer, Double> map;
	
	//generate the frequency of notes
	public NotePitch()
	{
		map = new HashMap<Integer, Double>();
		
		//from C3 to C7
		for(int ct = 28; ct<=88; ct++)
		{
			map.put(ct, Math.pow(2, (double)(ct - 49)/12) * 440);
		}
	}
	
	public double get(int i)
	{
		return map.get(i);
	}
}
