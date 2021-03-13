import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Zilong He
 * @version 1.0
 */

public class Voting_System {
    /**
     * This class is the main class of the voting system.
     * This class has 5 attributes.
     * @return IOException.
     */
    public String votetype;
    public int totalballot, totalcandidate;

    public String getVotetype() {
        return votetype;
    }

    public int getTotalballot() {
        return totalballot;
    }

    public int getTotalcandidate() {
        return totalcandidate;
    }

    /**
     * This method is used to read input file.
     * @param String type, receive the name of the input file.
     * @return void.
     */
    public static void readFile(String Inputfile) throws FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("E:\\CSVDemo.csv"));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
                System.out.println(getRecordFromLine(scanner.nextLine()));
            }
        }
    }

    /**
     * This method is record each lines of input CSV file.
     * @param String type, receive a line of the input file.
     * @return String list, return the information list from the input line.
     */
    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    /**
     * This method is used to generate Voting_System instance and run the voting system.
     * @param String and integer type, receive type, total ballots and candidates of the election.
     */
    public Voting_System(String votetype, int totalballot, int totalcandidate) {
        this.votetype = votetype;
        this.totalballot = totalballot;
        this.totalcandidate = totalcandidate;
    }

//    public static void main(String[] args) {
//        // write your code here
//        System.out.println("Hello");
//        readFile("String Inputfile");
//    }
}
