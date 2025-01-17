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
	
	public Commit (String summary, String author) throws Exception {
		//getting the blobs added from index and making the tree object for this commit
		ArrayList<String> indexContents = getBlobsFromIndex();
		
		TreeObject pTree = new TreeObject(indexContents);
		
		String sha1 = Commit.encryptThisString(summary + "" + author + "" + readHead());
		
		//Making a new CommitNode to store the data
		CommitNode newNode = new CommitNode (summary, author, getDate(), pTree.getSha1(), sha1);
		
		//Changing the parent and child pointers as needed
		if (!readHead().equals("")) {
			setChild("objects"+File.separator+readHead(), newNode.getSha1());
			newNode.setParent(readHead());
		}
		node = newNode;
		File head = new File("HEAD");
		FileWriter headWriter = new FileWriter(head, false);
		headWriter.write(this.getSha1());
		headWriter.close();
		this.writeFile();
		this.clearIndex();
	}
	
	//Gets the blobs from the index as an ArrayList of Strings, with each entry in the form: "blob: <sha1> <fileName>" 	
	//Changed index entries to be of form: " <sha1> <filename>" for east addition to ArrayList
	private ArrayList<String> getBlobsFromIndex() throws IOException{
		Scanner indexScanner = new Scanner(new File("index.txt"));
		ArrayList<String> indexContents = new ArrayList<String>();
		boolean fileDeleted = false;
		//adding each blob from index
		while (indexScanner.hasNextLine()) {
			String indexEntry = "blob: ";
			String nextLine = indexScanner.nextLine();
			int colonIndex = nextLine.indexOf(":");
			
			//adding the sha1 and then the fileName
			if (nextLine.substring(0,10).equals("*deleted*")) {
				fileDeleted = true;
				for (String entry: getBlobsFromPreviousCommits(nextLine.substring(56), "objects" + File.separator + readHead())) {
					if (!indexContents.contains(entry)) {
						indexContents.add(entry);
					}
				}
			}
			else {
				indexEntry+=nextLine.substring(colonIndex+2);
				indexEntry+=" ";
				indexEntry+=nextLine.substring(0,colonIndex);
				indexContents.add(indexEntry);
			}
		}
		
		if (!fileDeleted) {
			String headContent = readHead();
			Scanner headScanner = new Scanner(headContent);
			if (!headContent.equals("")) {
				indexContents.add("tree: "+headScanner.nextLine());
			}
			headScanner.close();
		}
		return indexContents;
	}
	
	public ArrayList<String> getBlobsFromPreviousCommits (String deletedFile, String previousTree) throws FileNotFoundException{
		Scanner treeScanner = new Scanner(new File(previousTree));
		ArrayList<String> treeContents = new ArrayList<String>();
		boolean deletedFileInTree = false;
		//adding each blob from index
		String lastLine = "";
		while (treeScanner.hasNextLine()) {
			String nextLine = treeScanner.nextLine();
			
			if (nextLine.substring(0,4).equals("blob")) {
				if (nextLine.substring(48).equals(deletedFile)) {
					deletedFileInTree = true;
				}
				else {
					treeContents.add(nextLine);
				}
			}
			lastLine=nextLine;
		}
		if (deletedFileInTree) {
			treeContents.add(lastLine);
		}
		else {
			for (String entry: getBlobsFromPreviousCommits(deletedFile, lastLine.substring(6))){
				treeContents.add(entry);
			}
		}
		return treeContents;
	}
	public String getDate () {
		Date currentDate = new Date();
		return currentDate.toString();
	}
	
	public CommitNode getNode () {
		return node;
	}
	
	public String readHead() throws IOException {
		Path headPath = Paths.get("HEAD");
		return Files.readString(headPath);
	}
	
	public void setChild(String parent, String child) throws IOException {
		//constructing the new file contents for the parent
		Path parentPath = Paths.get(parent);
		String parentContent = Files.readString(parentPath);
		Scanner parentReader = new Scanner(parentContent);
		String editedParentContent = parentReader.nextLine()+"\n"+parentReader.nextLine()+"\n";
		parentReader.nextLine();
		editedParentContent+=child+"\n";
		editedParentContent+=parentReader.nextLine()+"\n"+parentReader.nextLine()+"\n"+parentReader.nextLine();
		
		//Writing to change the parent file
		File parentFile = new File(parent);
		FileWriter parentWriter = new FileWriter(parentFile, false);
		parentWriter.write(editedParentContent);
		parentWriter.close();
	}
	public void writeFile () throws IOException {
		StringBuilder fileString = new StringBuilder();
		fileString.append (node.getPTree() + "\n");
		if (!node.getParent().equals("")) {
			fileString.append(node.getParent() + "\n");
		}
		else {
			fileString.append("null\n");
		}
		if (!node.getChild().equals("")) {
			fileString.append(node.getChild() + "\n");
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
