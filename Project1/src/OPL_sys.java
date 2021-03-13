/*
* @author Chenxuan Liu
*/

import java.io.*;
import java.util.*;
import javax.swing.plaf.basic.*;

/**
 * This class is used for OPL voting.
 * OPL_sys is a class that creat for open party list ballot voting type.
 * It must need two other class, which is party and candidate to complete the task.
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
    Scanner scanner;

    /**
     * Constructor, creates new OPL_sys instance.
     * @param candidate Candidate ArrayList which contains all the candidates in the election.
     * @param party Party ArrayList stores all the parties that have joined the election.
     * @param number_candidate the total number of candidates added to the election.
     * @param num_seats the total number of seats won.
     * @param total_ballot the total number of popular votes allocated to parties.
     * @param scanner java scanner type used to help reading information and ballots from input file.
     */
    public OPL_sys(ArrayList<Candidate> candidate, ArrayList<Party> party, int number_candidate, int num_seats, int total_ballot, Scanner scanner){
        this.candidates = candidate;
        this.parties = party;
        this.num_candidate = number_candidate;
        this.num_seats = num_seats;
        this.total_ballot = total_ballot;
        this.allocated_seats = 0;
        this.scanner = scanner;
    }

    /**
     * Reads ballots from the CSV file.
     * @param scanner used to read file.
     * @return void.
     * @exception no exception.
     */
    public void readballot(Scanner scanner){
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            for(int i = 0; i < line.length(); i++){
                if(line.charAt(i) == '1'){
                    int vote = candidates.get(i).getVote();
                    candidates.get(i).setVote(vote+1);
                    break;
                }
            }
        }
        
        for(int i = 0; i < parties.size(); i++){
            Party party = parties.get(i);
            int vote = party.getVote();
            ArrayList<Candidate> members = party.getMembers();
            for(int j = 0; j < members.size(); j++){
                vote = vote + members.get(j).getVote();
            }
            party.setVote(vote);
        }
    }
    

    /**
	 * Allocates the seats for each party based on the quota.
	 * @param args Unused.
	 * @return return an arraylist<int> that contains the seats allocated by the party in order.
     * @exception no exception.
	 */
    public ArrayList<Integer> firstround_Seats(){
        int quota = total_ballot/num_seats;
        ArrayList<Integer> partySeats = new ArrayList<>();
        for (Party party: parties){
            int vote = party.getVote();
            int seats = vote/quota;
            
            //if more seats are allocted to a party
            //free exceeding seats
            if(party.getMembers().size() < seats){
                seats = party.getMembers().size();
                party.setVote(-1); //everyone get a seat, discard all votes
            } else{
                party.setVote(vote - (seats * quota));
            }
            
            allocated_seats = allocated_seats + seats;
            partySeats.add(seats);
        }
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
     * Finds the party that receives the most votes.
     * @param args Unused.
     * @return return the index of the party which received the most votes.
     * @exception no exception.
     */
    public int findlargestvote(){
        int index = 0;
        int largest_vote = parties.get(index).getVote();
        for(int i = 0; i < parties.size(); i++){
            int vote = parties.get(i).getVote();
            if(vote > largest_vote){
                index = i;
                largest_vote = vote;
            } else if (vote == largest_vote){
                int[] random = {index, i};
                index = random[coin.flip(2)];
            }
        }
        return index;
    }
    
    
    //need java docs here
    //need the partyseats list from the first round
    public ArrayList<Integer> secondround_seats(ArrayList<Integer> partyseats){
        for(int i = 0; i < parties.size(); i++){
            if(!checkRemainSeats()){
                return partyseats;
            } else {
                int index = findlargestvote();
                int seats = partyseats.get(index);
                parties.get(index).setVote(-1);
                partyseats.set(index, seats + 1);
            }
        }
        return partyseats;
    }


    /**
	 * Finds the largest vote for a candidate in a candidate arraylist.
	 * @param Candidates ArrayList<Candidate> type, takes an array of candidates as input.
	 * @return return the index of the candidate with the most votes in the candidate arraylist.
     * @exception no exception.
	 */
    private int findLargestCandidate(ArrayList<Candidate> Candidates){
        int index = 0;
        int numofTie = 1;
        ArrayList<Integer> Tielist = new ArrayList<>();
        Tielist.add(index);
        
        for(int i = 0; i < candidates.size(); i++){
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
            return Tielist.get(coin.flip(numofTie));
        }
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
        for(int i = 0; i < parties.size(); i++){
            int seat = partyseats.get(i);
            Party party = parties.get(i);
            while (seat > 0) {
                int index = findLargestCandidate(party.getMembers());
                Candidate winner = party.getMembers().get(index);
                winner.setVote(-1);
                winners.add(winner);
                seat--;
            }
        }
        return winners;
    }
}
