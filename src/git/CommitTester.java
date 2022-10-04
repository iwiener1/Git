package git;

public class CommitTester {

	public static void main(String[] args) throws Exception {
		Index i = new Index();
		i.start();
		i.add("test.txt");
		Commit a  = new Commit (null, "cool", "Henry");
		
		/**
		i.add("test2.txt");
		i.add("test3.txt");
		Commit b = new Commit (a.getNode(), "cooler", "Henry");
		b.writeFile();
		**/
	}

}
