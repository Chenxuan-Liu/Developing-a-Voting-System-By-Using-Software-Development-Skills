/*
* @author Chenxuan Liu
*/

import java.io.*;
import java.util.*;
import javax.swing.plaf.basic.*;



/**
 * OPL_sys is a class that creat for open party list ballot voting type.
 * It must need two other class, which is party and candidate to complete the task.
 * 
 *
 * Author: Chenxuan Liu
 */

public class OPL_sys{
    private ArrayList<Candidate> candidates;
    private ArrayList<Party> parties;
    private int num_candidate, num_seats, total_ballot, allocated_seats;
    private Coin_Flip coin = new Coin_Flip();
    Scanner scanner;

    public OPL_sys(ArrayList<Candidate> candidate, ArrayList<Party> party, int number_candidate, int num_seats, int total_ballot, Scanner scanner){
        this.candidates = candidate;
        this.parties = party;
        this.num_candidate = number_candidate;
        this.num_seats = num_seats;
        this.total_ballot = total_ballot;
        this.allocated_seats = 0;
        this.scanner = scanner;
    }
    
    
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
	 * allocate the seats for each party based on the quota
	 *
	 * @return returna an arraylist<int> that contains the seats allocated by the party in order.
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
    
    
    
    
    public boolean checkRemainSeats(){
        return allocated_seats < num_seats;
    }
    
    
    
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
    }


    /**
	 * find the largest vote for a candidate in a candidate arraylist
	 *
	 * @param ArrayList<Candidate> take an array of candidates as input
	 * @return return the candidate with the most votes in the candidate arraylist
	 */

    public Candidate findLargestCandidate(ArrayList<Candidate> Candidates){
        Candidate candidate = candidates.get(0);
        int numofTie = 1;
        ArrayList<Candidate> numofTielist = new ArrayList<>();
        numofTielist.add(candidate);
        
        for(int i = 0; i < candidates.size(); i++){
            Candidate c = candidates.get(i);
            if(c.getVote() > candidate.getVote()){
                candidate = c;
                numofTie = 1;
                numofTielist.clear();
                numofTielist.add(candidate);
            } else if(c.getVote() == candidate.getVote()){
                numofTie++;
                numofTielist.add(c);
            }
        }
        
        if(numofTie > 1){
            return numofTielist.get(coin.flip(numofTie));
        }
        return candidate;
    }
}




//////////function above this line is rewrited based on jicheng's idea//////////////////
//////////function  below are not working yet//////////////////////////////////////////





//
//    /**
//	 * find the candidates for each party that are selected.
//	 *
//	 * @param ArrayList<int> take number of seats for each party as input
//	 * @return return an arraylist of candidates that are selected
//	 */
//
//    public ArrayList<Candidate> insidePartyElection(ArrayList<Integer> numOfSeat){
//        ArrayList<Candidate> selectedCandidate = new ArrayList<>();
//        for (int i = 0; i < party.size(); i++){
//            for(int chosenSeat = 0; chosenSeat < numOfSeat.get(i); chosenSeat++){
//                selectedCandidate.add(findLargestCan(party.get(i).getMembers()));
//            }
//        }
//        return selectedCandidate;
//    }
//
//
//    /**
//	 * this method call the previous method to complete the OPL voting.
//	 *
//	 * @return return an arraylist of candidates as winner.
//	 */
//
//    public ArrayList<Candidate> run_OPL(Scanner sc){
//
//        // readBallot(sc);
//
//        ArrayList<Integer> allocated  = updateSeat();
//
//        while (checkRemainSeats() != 0){
//            // find remaining votes
//            ArrayList<Integer> remainVotes = remainingVotes(party);
//            int largest = 0;
//            boolean haveTie = false;
//            int numOfTie = 0;
//            ArrayList<Integer> indexOfTie = new ArrayList<>();
//            // find the largest vote party and check for the tie condition
//            for (int i: remainVotes){
//                if (i > largest) {
//                    indexOfTie.clear();
//                    largest = i;
//                    haveTie = false;
//                    numOfTie = 0;
//                    indexOfTie.add(i);
//                } else if (i ==  largest){
//                    haveTie = true;
//                    numOfTie += 1;
//                    indexOfTie.add(i);
//                }
//            }
//
//            // if (haveTie) {
//            //     // CoinFlip rand =  new CoinFlip(numOfTie)
//            // }
//
//            int index = indexOfTie.get(0);
//            allocated.set(index, allocated.get(index) + 1);
//            Party largestParty = party.get(index);
//            largestParty.setVote(-1);
//        }
//
//        ArrayList<Candidate> winner = insidePartyElection(allocated);
//
//        return winner;
//    }
//
//}