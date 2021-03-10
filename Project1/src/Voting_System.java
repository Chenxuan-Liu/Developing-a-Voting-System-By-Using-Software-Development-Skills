import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public static void readFile(String Inputfile) throws FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("E:\\CSVDemo.csv"));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
                System.out.println(getRecordFromLine(scanner.nextLine()));
            }
        }
    }

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
