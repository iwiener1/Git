package git;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class CommitNode {
	private String pTree;
	private String summary;
	private String author;
	private String date;
	private String parent;
	private String child;
	private String sha1;
	
	
	public CommitNode (String summary, String author, String date, String pTree, String sha1) {
		this.pTree = pTree;
		this.summary = summary;
		this.author = author;
		this.date = date;
		this.sha1 = sha1;
	}
	
	public String getPTree () {
		return pTree;
	}
	
	public String getSummary () {
		return summary;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getDate () {
		return date;
	}
	
	public String getSha1() {
		return sha1;
	}
	
	public void setParent (String parent) {
		this.parent = parent;
	}
	
	public void setChild (String child) {
		this.child = child;
	}
	
	public CommitNode getParent () {
		return parent;
	}
	
	public String getChild () {
		return child;
	}
	
	//reads the file for a commit and sets all private instance variables equal to those of the file
	public void setToFile(String fileName) throws IOException {
		Path filePath = Paths.get(fileName);
		String fileContents = Files.readString(filePath);
		Scanner reader = new Scanner(fileContents);
		pTree = reader.nextLine();
		parent = reader.nextLine();
		child = reader.nextLine();
		author = reader.nextLine();
		date = reader.nextLine();
		summary = reader.nextLine();
	}
}
