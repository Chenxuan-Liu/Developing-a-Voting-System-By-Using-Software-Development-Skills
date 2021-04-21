import java.io.*;
import java.util.*;
import javax.swing.plaf.basic.*;

/**
 * This class is used for OPL voting.
 * OPL_sys is a class that creat for open party list ballot voting type.
 * It must need two other class, which is party and candidate to complete the task.
 * Calculate the quota for each party, if one party’s seat number is greater than its candidate number, distribute the extra seats to the other party.
 * Finish quotas and allocate seats to party candidates. For any tie in the vote count, a coin flip is used to settle the tie.
 * <p></p>
 * This class has 8 attributes.
 *
 * @author Chenxuan Liu
 * @author Jicheng Zhu
 * @version 1.0
 */

public class OPL_sys{

    private ArrayList<Candidate> candidates;
    private ArrayList<Party> parties;
    private int num_candidate, num_seats, total_ballot, allocated_seats;
    private Coin_Flip coin = new Coin_Flip();
    //Scanner scanner;
    private PrintWriter mywriter;

    /**
     * Constructor, creates new OPL_sys instance.
     * @param candidate Candidate ArrayList which contains all the candidates in the election.
     * @param party Party ArrayList stores all the parties that have joined the election.
     * @param number_candidate the total number of candidates added to the election.
     * @param num_seats the total number of seats won.
     * @param total_ballot the total number of popular votes allocated to parties.
     * @param scanner java scanner type used to help reading information and ballots from input file.
     */
    public OPL_sys(ArrayList<Candidate> candidate, ArrayList<Party> party, int number_candidate, int num_seats, PrintWriter mywriter){
        this.candidates = candidate;
        this.parties = party;
        this.num_candidate = number_candidate;
        this.num_seats = num_seats;
        this.total_ballot = 0;
        this.allocated_seats = 0;
        //this.scanner = scanner;
        this.mywriter = mywriter;
        
    }

    /**
     * Reads ballots from the CSV file.
     * @param scanner used to read file.
     * @return void.
     * @exception no exception.
     */
    public void readballot(int num_ballot, Scanner scanner){
        int index = total_ballot + 1;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            mywriter.printf("No.%d ballot is %s.%n",index,line);
            line = line.replaceAll("\\s",""); //remove whitespace
            for(int i = 0; i < line.length(); i++){
                if(line.charAt(i) == '1'){
                    Candidate candidate = candidates.get(i);
                    mywriter.printf("No.%d ballot assign to %s from party %s %n",index,candidate.getName(),candidate.getParty());
                    int vote = candidate.getVote();
                    candidates.get(i).setVote(vote+1);
                    mywriter.printf("%s has %d vote(s) now.%n",candidate.getName(),candidate.getVote());
                    break;
                }
            }
            index++;
        }
        mywriter.println("all ballots from this file are processed.");
        //count vote for each party
        for(int i = 0; i < parties.size(); i++){
            Party party = parties.get(i);
            int vote = party.getVote();
            ArrayList<Candidate> members = party.getMembers();
            for(int j = 0; j < members.size(); j++){
                vote = vote + members.get(j).getVote();
            }
            party.setVote(vote);
            mywriter.printf("Party %s has %d vote(s).%n",party.getName(),vote);
        }
        total_ballot = total_ballot + num_ballot;
        mywriter.flush();
    }
    

    /**
	 * Allocates the seats for each party based on the quota.
	 * @param args Unused.
	 * @return return an arraylist<int> that contains the seats allocated by the party in order.
     * @exception no exception.
	 */
    public ArrayList<Integer> firstround_Seats(){
        int quota = total_ballot/num_seats;
        mywriter.println("start allocate first round seats.");
        mywriter.printf("The quota is %d.%n",quota);
        ArrayList<Integer> partySeats = new ArrayList<>();
        for (Party party: parties){
            int vote = party.getVote();
            int seats = vote/quota;
            
            //if more seats are allocted to a party
            //free exceeding seats
            if(party.getMembers().size() < seats){
                seats = party.getMembers().size();
                mywriter.printf("Party %s has enough votes to get seats for everyone in the party.",party.getName());
                mywriter.println("Clear remaining votes.");
                party.setVote(-1); //everyone get a seat, discard all votes
            } else {
                party.setVote(vote - (seats * quota));
                mywriter.printf("Party %s get %d seats, the remaining number of votes is %d.%n", party.getName(), seats, party.getVote());
            }
            
            allocated_seats = allocated_seats + seats;
            partySeats.add(seats);
        }
        mywriter.flush();
        return partySeats;
    }



    /**
     * Checks whether the allocated seats is less than total number of seats.
     * @param args Unused.
     * @return true, if there exists remaining seats. Otherwise, no.
     * @exception no exception.
     */
    public boolean checkRemainSeats(){
        return allocated_seats < num_seats;
    }


    /**
     * Finds the party that receives the most remaining votes.
     * @param args Unused.
     * @return return the index of the party which received the most votes.
     * @exception no exception.
     */
    public int findlargestvote(){
        mywriter.println("Finding the party with largest remaining votes.");
        int numofTie = 1;
        int index = 0;
        ArrayList<Integer> Tielist = new ArrayList<>();
        Tielist.add(index);
        int largest_vote = parties.get(index).getVote();
        
        for(int i = 1; i < parties.size(); i++){
            int vote = parties.get(i).getVote();
            if(vote > largest_vote){
                index = i;
                numofTie = 1;
                Tielist.clear();
                Tielist.add(i);
                largest_vote = vote;
            } else if (vote == largest_vote){
                numofTie++;
                Tielist.add(i);
            }
        }
        
        if(numofTie > 1){
            for(int i:Tielist){
                mywriter.printf("Party %s ", parties.get(i).getName());
            }
            mywriter.println("have the same remaining votes. start a coin flip.");
            index = Tielist.get(coin.flip(numofTie));
            mywriter.printf("Party %s is chose by a coin flip.",parties.get(index).getName());
        } else {
            mywriter.printf("%s has the largest remaining votes.",parties.get(index).getName());
        }
        mywriter.flush();
        return index;
    }
    
    
    /**
     * Finds the party that receives the most remaining votes.
     * @param arraylist<int> that contains the seats allocated by the party in the first round.
     * @return arraylist<int> that contains the seats allocated by the party after the second round.
     * @exception no exception.
     */
    public ArrayList<Integer> secondround_seats(ArrayList<Integer> partyseats){
        mywriter.println("start allocate second round seats.");
        for(int i = 0; i < parties.size(); i++){
            if(!checkRemainSeats()){
                mywriter.println("No remaining seats, second round stop.");
                mywriter.flush();
                return partyseats;
            } else {
                int index = findlargestvote();
                int seats = partyseats.get(index);
                Party party = parties.get(index);
                party.setVote(-1);
                mywriter.printf("Party %s get a seat, clear remaining votes.%n",party.getName());
                partyseats.set(index, seats + 1);
                allocated_seats++;
                mywriter.printf("Party %s has %d seats now.%n",party.getName(),partyseats.get(index));
            }
        }
        mywriter.flush();
        return partyseats;
    }


    /**
     * Finds the largest vote for a candidate in a candidate arraylist.
     * @param Candidates ArrayList<Candidate> type, takes an array of candidates as input.
     * @return return the index of the candidate with the most votes in the candidate arraylist.
     * @exception no exception.
     */
    private int findLargestCandidate(ArrayList<Candidate> candidates){
        mywriter.println("Finding the candidate with the largest votes.");
        int index = 0;
        int numofTie = 1;
        ArrayList<Integer> Tielist = new ArrayList<>();
        Tielist.add(index);
        
        for(int i = 1; i < candidates.size(); i++){
            Candidate candidate = candidates.get(i);
            Candidate largestcandidate = candidates.get(index);
            if(candidate.getVote() > largestcandidate.getVote()){
                index = i;
                numofTie = 1;
                Tielist.clear();
                Tielist.add(i);
            } else if(candidate.getVote() == largestcandidate.getVote()){
                numofTie++;
                Tielist.add(i);
            }
        }
        
        if(numofTie > 1){
            for(int i:Tielist){
                mywriter.printf("%s from %s ",candidates.get(i).getName(),candidates.get(i).getParty());
            }
            mywriter.println("have the same votes. start a coin flip.");
            index = Tielist.get(coin.flip(numofTie));
            mywriter.printf("%s from %s is chose by a coin flip.",candidates.get(index).getName(),candidates.get(index).getParty());
        } else {
            mywriter.printf("%s from %s has largest votes.",candidates.get(index).getName(),candidates.get(index).getParty());
        }
        mywriter.flush();
        return index;
    }
    
    
    
    /**
    * Finds the candidates for each party that are selected.
    * @param partyseats ArrayList<Integer> type, takea number of seats for each party as input.
    * @return return an arraylist of candidates that are selected.
    * @exception no exception.
    */
    public ArrayList<Candidate> findwinnner(ArrayList<Integer> partyseats){
        ArrayList<Candidate> winners = new ArrayList<>();
        mywriter.println("start allocate seats for winners.");
        for(int i = 0; i < parties.size(); i++){
            int seat = partyseats.get(i);
            Party party = parties.get(i);
            mywriter.printf("Party %s get %d seats.%n",party.getName(),seat);
            while (seat > 0) {
                int index = findLargestCandidate(party.getMembers());
                Candidate winner = party.getMembers().get(index);
                mywriter.printf("%s from %s get a seat.%n",winner.getName(),party.getName());
                winner.setVote(-1);
                winners.add(winner);
                seat--;
            }
        }
        mywriter.flush();
        return winners;
    }
}
