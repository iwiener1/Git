package git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Blob {
	private String SHAString; 
	public Blob (String fileName) throws IOException {
		BufferedReader br = new BufferedReader (new FileReader (fileName));
		StringBuilder temp = new StringBuilder();
		while (br.ready()) {
			char c = (char) br.read();
			temp.append(c);
		} // copied entire contents of a file onto a StringBuilder
		br.close();
		String fileString = new String (temp.toString()); // fileString is string with entire contents of the file on it
		String SHAString = new String (SHA1.encryptThisString(fileString)); // SHAString is String with a SHAString of the contents of fileString
		this.SHAString = SHAString;
		
		File newBlob = new File ("objects/"+ SHAString); // new blob created in Testing/objects (objects folder will be created in Index class) with its name being the SHAString
		FileWriter myWriter = new FileWriter (newBlob); // connects FileWriter to the new file
		myWriter.write(fileString); // writes the contents of the original file onto this new file
		myWriter.close();;
		
	}
	
	public String getSHAString () {
		return SHAString;
	}
}
