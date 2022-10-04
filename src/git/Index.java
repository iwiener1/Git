package git;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Index {
	// iWriter will only be used in start(), to make the index file a .txt file
	// don't worry about it otherwise
	FileWriter iWriter;
	// blobStorage stores a key (fileName) and a value (its SHAString)
	private HashMap<String, String> blobStorage = new HashMap<String, String>();
	
	public Index () {	
	}
	
	//creating index file, making it a .txt; creating objects, making it a folder
	public void start () throws IOException {
		File index = new File ("index.txt");
		iWriter =  new FileWriter (index);
		File objectsFolder = new File("objects");
		objectsFolder.mkdir();
	}
	
	public void add (String fileName) throws IOException {
		// creating StringBuilder that will become the string contained in the index.txt file
		StringBuilder newString = new StringBuilder ();
		// using a scanner and while loop, inputting everything from index.txt into StringBuilder
		Scanner fileCopier = new Scanner (new File ("index.txt"));
		while (fileCopier.hasNextLine()) {
			String newLine = fileCopier.nextLine();
			newString.append(newLine);
			System.out.println (newLine);
			newString.append("\n");
		}
		fileCopier.close();
		// Now adding the entry for the new file into index.txt
		newString.append(fileName + " : ");
		Blob newBlob = new Blob (fileName);
		blobStorage.putIfAbsent(fileName, newBlob.getSHAString()); //adding to HashMap for use later in remove
		System.out.println (blobStorage.get(fileName)); // makes testing easier
		newString.append(blobStorage.get(fileName) + " " + fileName + "\n");
		FileWriter indexWriter = new FileWriter ("index.txt"); // assigning FileWriter to index.txt
		indexWriter.write(newString.toString()); // after finally completing the StringBuilder, it is being written to index.txt
		indexWriter.close();
	}
	
	public void remove (String fileName) throws IOException {
		int numOfCharsInFileName = fileName.length(); // keeping track of this so that we can search only the first part of a blob's entry in index.txt
		// for example, in Testing/sample.txt, we just want to read "sample.txt", so it's good to know that "sample.txt" is 10 chars
		File toDelete = new File ("objects/" + blobStorage.get(fileName));
		toDelete.delete();// deletes the desired file in objects folder
		Scanner fileScanner = new Scanner (new File ("index.txt")); // will scan through index.txt
		StringBuilder newString = new StringBuilder (); // new StringBuilder will eventually replace whatever text is currently in index.txt
		while (fileScanner.hasNextLine()) {
			String newLine = fileScanner.nextLine();
			if (!(newLine.substring(8 + numOfCharsInFileName).equals(fileName))) { // tests if the newLine is the one we want to remove. If it is not, it will be added to StringBuilder
				// thus resulting in everything but the object we want to delete being in the StringBuilder
				newString.append(newLine);
				newString.append("\n");
			}
		}
		FileWriter indexWriter = new FileWriter ("index.txt"); // finally writes StringBuilder to index.txt
		indexWriter.write(newString.toString());
		indexWriter.close();
	}
}
