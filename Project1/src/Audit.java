import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Audit {
	
	public PrintWriter createauditfile(String votetype){
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