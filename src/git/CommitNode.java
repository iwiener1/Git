package git;

public class CommitNode {
	private String pTree;
	private String summary;
	private String author;
	private String date;
	private CommitNode parent;
	private CommitNode child;
	
	
	public CommitNode (String summary, String author, String date) {
		this.pTree = pTree;
		this.summary = summary;
		this.author = author;
		this.date = date;
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
	
	public void setParent (CommitNode parent) {
		this.parent = parent;
	}
	
	public void setChild (CommitNode child) {
		this.child = child;
	}
	
	public CommitNode getParent () {
		return parent;
	}
	
	public CommitNode getChild () {
		return child;
	}
}
