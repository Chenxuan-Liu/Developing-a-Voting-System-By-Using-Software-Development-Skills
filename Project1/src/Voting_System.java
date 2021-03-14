import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This class is the main class of the voting system.
 * This class has 5 attributes.
 * @author Zilong He
 * @version 1.0
 */

public class Voting_System {

    public static String votetype;
    public static int totalballot, totalcandidate, totalseats;


    /**
     * Reads input file from the given address.
     * @param Inputfile String type, the name of the input file.
     * @return void.
     * @exception：FileNotFoundException
     */
    public static Scanner readFile(String Inputfile,ArrayList<Candidate> candidate, ArrayList<Party> party) throws FileNotFoundException {
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
        totalballot = Integer.parseInt(records.get(stopline-1).get(0));

        if (stopline==4){
            votetype = "IR";

            totalcandidate = Integer.parseInt(records.get(1).get(0));

            //Create candidate and Arraylist from Line 3
            for (int j = 0;j < totalcandidate;j++){
                String current_candidate = records.get(2).get(j);

                candidate.add(new Candidate(current_candidate.split("[\\(\\)]")[0],current_candidate.split("[\\(\\)]")[1]));
                if(j == 0){
                    party.add(new Party(current_candidate.split("[\\(\\)]")[1]));
                    party.get(j).addmember(candidate.get(j));
                }else {
                    boolean party_exist_flag = false;
                    int party_ID = 0;
                    for (int k = 0;k < party.size();k++){
                        if (party.get(k).getName().equals(current_candidate.split("[\\(\\)]")[1])){
                            party_exist_flag = true;
                            party_ID = k;
                            break;
                        }
                    }

                    if (party_exist_flag==true){
                        party.get(party_ID).addmember(candidate.get(j));
                    }else {
                        party.add(new Party(current_candidate.split("[\\(\\)]")[1]));
                        party.get(party.size()-1).addmember(candidate.get(j));
                    }
                }
            }
            //Create IR ballots from Line 4
        }
        else if (stopline == 5){
            votetype = "OPL";
            totalcandidate = Integer.parseInt(records.get(1).get(0));
            totalseats = Integer.parseInt(records.get(3).get(0));
            for (int j = 0;j < 2*totalcandidate;j=j+2) {
                
                String current_candidate = records.get(2).get(j);
                String current_party = records.get(2).get(j+1);


                candidate.add(new Candidate(current_candidate.split("[\\[\\]]")[1],current_party.split("[\\[\\]]")[0]));
                if(j == 0){
                    party.add(new Party(current_party.split("[\\[\\]]")[0]));
                    party.get(j).addmember(candidate.get(j));
                }else {
                    boolean party_exist_flag = false;
                    int party_ID = 0;
                    for (int k = 0;k < party.size();k++){
                        if (party.get(k).getName().equals(current_party.split("[\\[\\]]")[0])){
                            party_exist_flag = true;
                            party_ID = k;
                            break;
                        }
                    }

                    if (party_exist_flag==true){
                        party.get(party_ID).addmember(candidate.get(j/2));
                    }else {
                        party.add(new Party(current_party.split("[\\[\\]]")[0]));
                        party.get(party.size()-1).addmember(candidate.get(j/2));
                    }
                }

            }
        }

        return scanner;
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

    /**
     * Main function, uses other classes to run the voting system.
     * @param args used.
     * @return void.
     * @exception FileNotFoundException not find file.
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Please input the path of input file");
        Scanner scn = new Scanner(System.in);
        String input = scn.nextLine();


        ArrayList<Candidate> candidate = new ArrayList<Candidate>();
        ArrayList<Party> party = new ArrayList<Party>();
        totalballot = 0;
        Audit myaudit = new Audit();


        Scanner BS = readFile(input,candidate,party);
        PrintWriter pwrite = myaudit.createauditfile(votetype);

        if (votetype.equals("IR")) {
            System.out.println("Total number of candidates: " + candidate.size());
            pwrite.printf("Total number of candidates: %d.%n",candidate.size());

            for (Candidate k:candidate){
                System.out.println(k.getName() + " from the party " + k.getParty());
                pwrite.println(k.getName() + " from the party " + k.getParty());
            }

            System.out.println("Total number of ballots: " + totalballot);
            pwrite.println("Total number of ballots: " + totalballot);

            IR_sys ir = new IR_sys(candidate, party, candidate.size(), 1, totalballot, BS,pwrite);

            ir.readballot(ir.scanner);
            Candidate winner;
            while ( (winner = ir.haswinner()) == null) {
                ir.redistribution();
            }
            System.out.println("The winner is " + winner.getName() + " from the party " + winner.getParty());
            pwrite.flush();
        } else if (votetype.equals("OPL")){

            System.out.println("Total number of candidates: " + candidate.size());
            pwrite.printf("Total number of candidates: %d.%n",candidate.size());

            for (Candidate k:candidate){
                System.out.println(k.getName() + " from the party " + k.getParty());
                pwrite.println(k.getName() + " from the party " + k.getParty());
            }

            System.out.println("Total number of seats: " + totalseats);
            pwrite.println("Total number of seats: " + totalseats);

            System.out.println("Total number of ballots: " + totalballot);
            pwrite.println("Total number of ballots: " + totalballot);

            OPL_sys opl = new OPL_sys(candidate,party,candidate.size(),totalseats,totalballot,BS,pwrite);

            opl.readballot(opl.scanner);

            ArrayList<Integer> partySeats = opl.firstround_Seats();
            ArrayList<Integer> partySeats2 = opl.secondround_seats(partySeats);
            ArrayList<Candidate> winner = opl.findwinnner(partySeats2);

            System.out.println("The winner(s) is/are");
            pwrite.println();
            pwrite.println("The winner(s) is/are");
            for (int i=0;i<winner.size();i++){
                System.out.println(winner.get(i).getName() + " from the party " + winner.get(i).getParty());
                pwrite.println(winner.get(i).getName() + " from the party " + winner.get(i).getParty());
            }
            pwrite.flush();
        }
        pwrite.close();
    }
}
