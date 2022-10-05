package git;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommitTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Path p1 = Paths.get("test1.txt");
        Files.writeString(p1, "Example", StandardCharsets.ISO_8859_1);
        
        Path p2 = Paths.get("test2.txt");
        Files.writeString(p2, "Fortnite", StandardCharsets.ISO_8859_1);
        
        Path p3 = Paths.get("test3.txt");
        Files.writeString(p3, "Fortnut", StandardCharsets.ISO_8859_1);
        
        Path p4 = Paths.get("test4.txt");
        Files.writeString(p4, "the one piece is real", StandardCharsets.ISO_8859_1);
        
        Path p5 = Paths.get("test5.txt");
        Files.writeString(p5, "heheheha", StandardCharsets.ISO_8859_1);
        
        Path p6 = Paths.get("test6.txt");
        Files.writeString(p6, "Mr Topics Man", StandardCharsets.ISO_8859_1);
        
        Path p7 = Paths.get("test7.txt");
        Files.writeString(p7, "Ayo the pizza here", StandardCharsets.ISO_8859_1);
        
        Path p8 = Paths.get("test8.txt");
        Files.writeString(p8, "Bazinga", StandardCharsets.ISO_8859_1);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File test1 = new File("test1.txt");
		test1.delete();
		
		File test2 = new File("test2.txt");
		test2.delete();
		
		File test3 = new File("test3.txt");
		test3.delete();
		
		File test4 = new File("test4.txt");
		test4.delete();
		
		File test5 = new File("test5.txt");
		test5.delete();
		
		File test6 = new File("test6.txt");
		test6.delete();
		
		File test7 = new File("test7.txt");
		test7.delete();
		
		File test8 = new File("test8.txt");
		test8.delete();
	}

	@Test
	void test() throws Exception {
		Index i = new Index();
		i.start();
		i.add("test.txt");
		i.add("test8.txt");
		Commit a  = new Commit ("cool", "Henry");
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
