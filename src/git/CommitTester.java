package git;

public class CommitTester {

	public static void main(String[] args) throws Exception {
		Index i = new Index();
		i.start();
		i.add("test.txt");
		i.add("test8.txt");
		Commit a  = new Commit ("cool", "Henry");
		i.add("test2.txt");
		i.add("test3.txt");
		Commit b = new Commit ("cooler", "Henry");
		i.add("test4.txt");
		i.add("test5.txt");
		Commit c = new Commit ("even cooler", "Isaac");
		i.add("test6.txt");
		i.add("test7.txt");
		Commit d = new Commit ("coolest", "Isaac");
	}

}
