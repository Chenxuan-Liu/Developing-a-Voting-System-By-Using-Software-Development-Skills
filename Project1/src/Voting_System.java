import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void setVotetype(String votetype) {
        this.votetype = votetype;
    }

    public void setTotalballot(int totalballot) {
        this.totalballot = totalballot;
    }

    public void setTotalcandidate(int totalcandidate) {
        this.totalcandidate = totalcandidate;
    }

    public static Scanner readFile(String Inputfile,String votetype,ArrayList<Candidate> candidate, ArrayList<Party> party) throws FileNotFoundException {
        File tempFile = new File(Inputfile);
        boolean exists = tempFile.exists();
        if (exists == false){
            System.out.println("No input file found. Program terminated");
            System.exit(0);
        }

        List<List<String>> records = new ArrayList<>();
        int stopline = -1,i = 0,totalcandidate = 0;
        Scanner scanner = new Scanner(new File(Inputfile));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
            if (i == 0) {
//                    System.out.println(Arrays.toString(records.toArray()));
//                    System.out.println(records.get(i));
//                System.out.println(records.get(i).get(i));
                if (new String("IR").equals(records.get(i).get(i))) {
                    System.out.println("You are in IR mode");
                    stopline = 4;
                }
                if (new String("OPL").equals(records.get(i).get(i))) {
                    System.out.println("You are in OPL mode");
                    stopline = 5;
                }
            }

            if (i == stopline - 1) {
                break;
            }
            i++;
        }
//        System.out.println(Arrays.toString(records.toArray()));

        if (stopline==4){
            votetype = "IR";
            totalcandidate = Integer.parseInt(records.get(1).get(0));

            //Create candidate and Arraylist from Line 3
            for (int j = 0;j < totalcandidate;j++){
//                System.out.println(records.get(2).get(j));
                String current_candidate = records.get(2).get(j);
//                current_candidate = current_candidate.replaceAll("\\s", ""); // Replace space as null
//                System.out.println(current_candidate);
//                System.out.println(current_candidate.split("[\\(\\)]")[0]); // Get name
//                System.out.println(current_candidate.split("[\\(\\)]")[1]); // Get party

                candidate.add(new Candidate(current_candidate.split("[\\(\\)]")[0],current_candidate.split("[\\(\\)]")[1]));
                party.add(new Party(current_candidate.split("[\\(\\)]")[1]));
            }

            //Create IR ballots from Line 4

        }
        else if (stopline == 5){
            votetype = "OPL";
        }

        return scanner;
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

    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println("Please input the path of input file");
//        Scanner scn = new Scanner(System.in);
//        String input = scn.nextLine();
//        readFile(input);

//        readFile("C:\\Users\\67307\\Documents\\CSCI 5801\\repo-Team11\\Project1\\csvfile\\OPL_overseats_doubleties.csv");
        String votetype = null;
        ArrayList<Candidate> candidate = new ArrayList<Candidate>();
        ArrayList<Party> party = new ArrayList<Party>();
        Scanner BS = readFile("C:\\Users\\67307\\Documents\\CSCI 5801\\repo-Team11\\Project1\\csvfile\\IR_direct_winner.csv",votetype,candidate,party);

        //Next is used for testing
        List<List<String>> allballots = new ArrayList<>();
        while (BS.hasNextLine()) {
            allballots.add(getRecordFromLine(BS.nextLine()));
        }
        System.out.println(Arrays.toString(allballots.toArray()));
    }
}
