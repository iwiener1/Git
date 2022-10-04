package git;
import java.util.*;
import java.io.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class Commit {
	private CommitNode node;
	
	public Commit (CommitNode parent, String summary, String author) {
		CommitNode newNode = new CommitNode (summary, author, getDate());
		if (parent != null) {
			parent.setChild(newNode);
			newNode.setParent(parent);
		}
		node = newNode;
		ArrayList<String> indexBlobs = getBlobsFromIndex();
		TreeObject cTree = new TreeObject(indexBlobs);
		
		String sha = Commit.encryptThisString("" + summary + "" + author + "" + parent);
		
	}
	
	//Gets the blobs from the index as an ArrayList of Strings, with each entry in the form: "blob: <sha1> <fileName>" 	
	//Changed index entries to be of form: " <sha1> <filename>" for east addition to ArrayList
	private ArrayList<String> getBlobsFromIndex() throws FileNotFoundException{
		Scanner indexScanner = new Scanner(new File("index.txt"));
		ArrayList<String> indexBlobs = new ArrayList<String>();
		while (indexScanner.hasNextLine()) {
			String indexEntry = "blob: ";
			indexEntry+=indexScanner.nextLine();
			indexBlobs.add(indexEntry);
		}
		return indexBlobs;
	}
	
	public String getDate () {
		return Commit.encryptThisString(node.getDate());
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
		if (node.getChild() != null ) {
			fileString.append("objects/" + node.getChild().getPTree() + "\n");
		}
		fileString.append ("" + node.getAuthor() + "\n");
		fileString.append("" + node.getDate () + "\n");
		fileString.append("" + node.getSummary());
		File newFile = new File ("Testing/objects/" + getDate());
		FileWriter fileWritey =new FileWriter (newFile);
		fileWritey.write(fileString.toString());
		fileWritey.close();
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
