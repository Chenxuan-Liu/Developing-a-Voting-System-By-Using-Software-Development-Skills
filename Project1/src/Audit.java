import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
* Used to create the audit file based on time and date.
* Also return the printwriter for further use.
* @author Jicheng Zhu
* @version 1.0
*/
public class Audit {
	
	/**
	* Returns the number of the randomly selected candidate among several tied candidates.
	* @param votetype A string indicates the vote type.
	* @return A printwriter used to write into the audit file.
	* @exception No exception
	*/
	public static PrintWriter createauditfile(String votetype) {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH:mm:ss");
		String filename = myDateObj.format(myFormatObj);
		filename = votetype + "_" + filename + ".txt";
		try{
			FileWriter filewriter = new FileWriter(filename);
			PrintWriter mywriter = new PrintWriter(filewriter);
			mywriter.println(votetype + " audit file created.");
			mywriter.flush();
			return mywriter;
		} catch (IOException e) {
			System.out.println("Failed to create audit file");
			e.printStackTrace();
		}
		return null;
	}
}