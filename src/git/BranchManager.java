package git;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BranchManager {
	private HashMap<String, Commit> branches;
	private Commit previous;
	private String currentBranch;
	
	public BranchManager() {
		branches = new HashMap<String, Commit>();
		
		//making the head file
		Path p = Paths.get("HEAD");
        try {
            Files.writeString(p, "", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //making the Branches file
        p = Paths.get("Branches");
        try {
            Files.writeString(p, "", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
		
	}
	
	public void addBranch(String branchName, Commit commit) throws IOException {
		File branchesFile = new File("Branches");
		FileWriter writer = new FileWriter(branchesFile, true);
		writer.write(branchName + ": ");
	}
	
	public void chooseBranch(String branch) throws IOException {
		currentBranch = branch;
		
		File head = new File("HEAD");
		FileWriter writer = new FileWriter(head, false);
		writer.write(branches.get(branch).getSha1());
	}
	
	public void updateForNewCommit(Commit commit) {
		if (branches.containsKey(currentBranch)) {
			previous = branches.get(currentBranch);
			previous.getNode().setChild(commit.getNode());
			previous.writeFile();
			branches.replace(currentBranch, commit);
		}
		else {
			branches.put(currentBranch, commit);
		}
		
		
	}
	
	
	
}

