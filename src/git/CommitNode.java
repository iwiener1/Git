package git;

public class CommitNode {
	private String pTree;
	private String summary;
	private String author;
	private String date;
	private CommitNode parent;
	private CommitNode child;
	private String sha1;
	private Commit commit;
	
	
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
	
	public void setParent (CommitNode parent) {
		this.parent = parent;
	}
	
	public void setChild (CommitNode child) {
		this.child = child;
	}
	
	public void setCommit(Commit commit) {
		this.commit=commit;
	}
	
	public Commit getCommit() {
		return commit;
	}
	public CommitNode getParent () {
		return parent;
	}
	
	public CommitNode getChild () {
		return child;
	}
	
}
