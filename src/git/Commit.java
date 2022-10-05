package git;
import java.util.*;
import java.io.*;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class Commit {
	private CommitNode node;
	
	public Commit (CommitNode parent, String summary, String author, Index index) throws Exception {
		//getting the blobs added from index and making the tree object for this commit
		ArrayList<String> indexContents = getBlobsFromIndex();
		if (parent!=null){
			indexContents.add("tree: "+parent.getSha1());
		}
		TreeObject pTree = new TreeObject(indexContents);
		
		String sha1 = Commit.encryptThisString("" + summary + "" + author + "" + headContent);
		
		//Making a new CommitNode to store the data
		CommitNode newNode = new CommitNode (summary, author, getDate(), pTree.getSha1(), sha1);
		if (parent != null) {
			parent.setChild(newNode);
			newNode.setParent(parent);
		}
		
		File head = new File("HEAD");
		FileWriter headWriter = new FileWriter(head, false);
		headWriter.append(this.getSha1());
		node = newNode;
		this.writeFile();
		this.clearIndex();
	}
	
	//Gets the blobs from the index as an ArrayList of Strings, with each entry in the form: "blob: <sha1> <fileName>" 	
	//Changed index entries to be of form: " <sha1> <filename>" for east addition to ArrayList
	private ArrayList<String> getBlobsFromIndex() throws FileNotFoundException{
		Scanner indexScanner = new Scanner(new File("index.txt"));
		ArrayList<String> indexContents = new ArrayList<String>();
		//adding each blob from index
		while (indexScanner.hasNextLine()) {
			String indexEntry = "blob: ";
			String nextLine = indexScanner.nextLine();
			int colonIndex = nextLine.indexOf(":");
			
			//adding the sha1 and then the fileName
			indexEntry+=nextLine.substring(colonIndex+2);
			indexEntry+=" ";
			indexEntry+=nextLine.substring(0,colonIndex);
			indexContents.add(indexEntry);
		}
		return indexContents;
	}
	
	public String getDate () {
		Date currentDate = new Date();
		return currentDate.toString();
	}
	
	public CommitNode getNode () {
		return node;
	}
	
	public void writeFile () throws IOException {
		StringBuilder fileString = new StringBuilder();
		fileString.append ("objects/" + node.getPTree() + "\n");
		if (node.getParent() != null ) {
			fileString.append("objects/" + node.getParent().getPTree() + "\n");
		}
		else {
			fileString.append("null\n");
		}
		if (node.getChild() != null ) {
			fileString.append("objects/" + node.getChild().getPTree() + "\n");
		}
		else {
			fileString.append("null\n");
		}
		fileString.append ("" + node.getAuthor() + "\n");
		fileString.append("" + node.getDate () + "\n");
		fileString.append("" + node.getSummary());
		File newFile = new File ("objects/" + node.getSha1());
		FileWriter fileWritey =new FileWriter (newFile);
		fileWritey.write(fileString.toString());
		fileWritey.close();
	}
	
	public void clearIndex() throws IOException {
		File index = new File("index.txt");
		//Making a new FileWriter NOT in append mode to rewrite the file to be blank;
		FileWriter deleter = new FileWriter(index, false);
		
	}
	
	public String getSha1() {
		return node.getSha1();
	}

	 public static String encryptThisString(String input)
	    {
	        try {
	            // getInstance() method is called with algorithm SHA-1
	            MessageDigest md = MessageDigest.getInstance("SHA-1");
	 
	            // digest() method is called
	            // to calculate message digest of the input string
	            // returned as array of byte
	            byte[] messageDigest = md.digest(input.getBytes());
	 
	            // Convert byte array into signum representation
	            BigInteger no = new BigInteger(1, messageDigest);
	 
	            // Convert message digest into hex value
	            String hashtext = no.toString(16);
	 
	            // Add preceding 0s to make it 32 bit
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
	 
	            // return the HashText
	            return hashtext;
	        }
	 
	        // For specifying wrong message digest algorithms
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }
	
	
}
