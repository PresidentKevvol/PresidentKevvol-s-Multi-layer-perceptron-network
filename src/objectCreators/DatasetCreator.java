package objectCreators;

import java.io.File;
import java.util.Vector;

import trainingObjects.DataEntry;
import trainingObjects.DataEntryCollection;
import trainingObjects.FileReading;
import trainingObjects.NoteNumberList;
import trainingObjects.StaticValues;

/**
 * fieldless class for reading the data files
 * and creating the dataset objects
 */
public class DatasetCreator
{
	/**
	 * read all training data files from a folder
	 * @param fname - folder name
	 * @return a DataEntryCollection object ready to be used for training
	 */
	public DataEntryCollection readFromFolder(String fname)
	{
		//get all the files from folder
		File f = new File(fname);
		File[] files = f.listFiles();
		NoteNumberList noteList = new NoteNumberList();
		
		//positive sample rate defined in StaticValues
		DataEntryCollection result = new DataEntryCollection(StaticValues.positiveRate);
		
		//read each file and create DataEntry's
		for(int ct=0; ct<files.length; ct++)
		{
			File curFile = files[ct];
			//name of the file, get actual name by cutting the ".txt" extension
			String nam = curFile.getName();
			String note = nam.replaceAll(".txt", "");
			
			//get the correct index from map(remember to subtract the lowestnote for reference point!)
			int corIndex = noteList.getFromName(note) - StaticValues.lowestNote;
			
			//extract DataEntry objects from file
			FileReading read = new FileReading(curFile, corIndex);
			Vector<DataEntry> entries = read.extract();
			
			result.add(entries);
		}
		
		return result;
	}
}
