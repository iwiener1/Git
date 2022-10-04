package git;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class TreeObject {
	private String sha1;
	
	public TreeObject (ArrayList <String> blobsAndTrees) throws FileNotFoundException
	{
		String contents = "";
		for (int k = 0; k < blobsAndTrees.size(); k++)
		{
			if (k != blobsAndTrees.size() - 1)
			{
				contents += blobsAndTrees.get(k) + "\n";
			}
			else
			{
				contents += blobsAndTrees.get(k);
			}
		}
		sha1 = encryptThisString (contents);
		new File("./objects").mkdir();
		File sha1File = new File ("./objects/" + sha1);
		PrintWriter printWriter = new PrintWriter (sha1File);
		printWriter.print(contents);
		printWriter.close();
	}
	
	public String getSha1(){
		return sha1;
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
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	}
}
