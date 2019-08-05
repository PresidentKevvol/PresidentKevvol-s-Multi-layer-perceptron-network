package trainingObjects;

import java.util.HashMap;

/**
 * list(map) of the notes' name and their midi number
 */
public class NoteNumberList
{
	HashMap<String, Integer> map;
	
	//initializer block populating the map
	{
		map = new HashMap<String, Integer>();
		
		//G3(35) to B5(63), the range of usual violin
		int min = 35;
		int max = 63;
		
		String[] noteNames = {"G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G"};
		
		//generates the notes in a loop
		for(int ct= min; ct<=max; ct++)
		{
			//octave of note
			int octave = (ct - 4) / 12 + 1;
			
			//name of note
			int note = ct % 12;
			String name = noteNames[note];
			
			//the complete name of the key(like C4, F#6)
			String keyName = name + octave;
			
			//put in the mapp
			map.put(keyName, ct);
		}
	}
	
	/**
	 * standard getter method for element in the map
	 * @param s - name of note
	 * @return corresponding midi index
	 */
	public int getFromName(String s)
	{
		return this.map.get(s);
	}
}
