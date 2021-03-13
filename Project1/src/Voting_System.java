import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is the main class of the voting system.
 * This class has 5 attributes.
 * @author Zilong He
 * @version 1.0
 */

public class Voting_System {

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
     * Reads input file from the given address.
     * @param Inputfile String type, the name of the input file.
     * @return void.
     * @exception no exception.
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
     * Records each lines of input CSV file.
     * @param line String type, a line of the input CSV file.
     * @return return the information list from the input line.
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
     * Generates Voting_System instance and runs the voting system.
     * @param votetype String type, the type of the election, IR or OPL.
     * @param totalballot integer type, the total number of votes received for the election.
     * @param totalcandidate integer type, the total number of candidates joined the election.
     * @exception no exception.
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
