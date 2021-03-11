/*
* @author Chenxuan Liu
*/

import java.io.*;
import java.util.*;



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
    private int number_candidate, num_seats, total_ballot;
    private Coin_Flip coin = new Coin_Flip();

    public OPL_sys(ArrayList<Candidate> candidate, ArrayList<Party> party, int number_candidate, int num_seats, int total_ballot){
        this.candidates = candidate;
        this.parties = party;
        this.number_candidate = number_candidate;
        this.num_seats = num_seats;
        this.total_ballot = total_ballot;
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
    
    
    
    
    //read ballot is finished  (I hope), need further change below
    
    
    

    /**
	 * Find the quota for the OPL_sys
	 *
	 * @return returna the quota as an integer
	 */

    public int findQuota(){
        return total_ballot/num_seats;

    }


    /**
	 * allocate the seats for each party based on the quota
	 *
	 * @return returna an arraylist<int> that contains the seats allocated by the party in order.
	 */

    public ArrayList<Integer> allocateSeats(){
        int quota = findQuota();
        ArrayList<Integer> partySeats = new ArrayList<>();
        for (Party p: party){
            if (p.getVote() != -1){
                partySeats.add(p.getVote()/quota);
            }
        }
        return partySeats;
    }


    /**
	 * check for the condition that the number of candidate in the party is smaller than the allocated seats
	 *
	 * @return returna the exceeding seats as integer.
	 */

    public int checkNumCanInParty(){
        ArrayList<Integer> numOfSeats = allocateSeats();
        ArrayList<Integer> numOfCandidate = new ArrayList<>();
        int remainingSeats = 0;

        for (Party p:party){
            numOfCandidate.add(p.getMembers().size());
        }

        for (int i = 1; i <= party.size(); i++){
            // have an error in the OPLvoting sys activity diagram
            if (numOfCandidate.get(i) < numOfSeats.get(i)){
                remainingSeats = remainingSeats + (numOfSeats.get(i) - numOfCandidate.get(i));
                party.get(i).setVote(-1);
                return remainingSeats;
            }
        }
        return remainingSeats;
    }

    
    /**
	 * find each party remained votes after first round
	 *
	 * @param ArrayList<Party> take an arraylist of party as input to access is vote and update.
	 * @return return an arraylist of integer that contain the remaining votes for each party in order
	 */

    public ArrayList<Integer> remainingVotes(ArrayList<Party> parties){
        ArrayList<Integer> remainingVotes = new ArrayList<>();
        int quota = findQuota();
        for (Party p: parties){
            if(p.getVote() != -1){  //check if choose the party with no candidate.
                remainingVotes.add(p.getVote()%quota);
            }
        }
        return remainingVotes;
    }


    /**
	 * update the seats by reduce the exceeding seats for some parties
	 *
	 * @return returna an arraylist of integer that contain the updated seats.
	 */

    public ArrayList<Integer> updateSeat(){
        ArrayList<Integer> numOfSeats = allocateSeats();
        ArrayList<Integer> numOfCandidate = new ArrayList<>();

        for (Party p:party){
            numOfCandidate.add(p.getMembers().size());
        }

        for (int i = 1; i <= party.size(); i++){
            if (numOfCandidate.get(i) < numOfSeats.get(i)){
                numOfSeats.set(i, numOfCandidate.get(i));
            }
        }

        return numOfSeats;
    }


    /**
	 * check for the remaining seats available
	 *
	 * @return return an integer of the remained seats that are available
	 */

    public int checkRemainSeats(){
        ArrayList<Integer> numOfSeats = updateSeat();
        int allocatedSeats = 0;
        for (int i: numOfSeats){
            allocatedSeats += i;
        }

        int remain = num_seats - allocatedSeats;
        
        return remain;
    }


    /**
	 * find the largest vote for a candidate in a candidate arraylist
	 *
	 * @param ArrayList<Candidate> take an array of candidates as input
	 * @return return the candidate with the most votes in the candidate arraylist
	 */

    public Candidate findLargestCan(ArrayList<Candidate> can){
        // remove the first element and set it to largest
        Candidate largest = can.remove(0);
        boolean hasTie = false;
        ArrayList<Integer> numOfTie = new ArrayList<>();
        int count = 0;
        for (Candidate c: can){
            count ++;
            if (c.getVote() > largest.getVote()){
                numOfTie.clear();
                largest = c;
                hasTie = false;
                numOfTie.add(count);
            } else if (c.getVote() == largest.getVote()){
                hasTie = true;
                numOfTie.add(count);
            }
        }
        

        // if (hasTie){
        //     coin.flip();
        // }
        
        largest.setVote(-1);
        return largest;
    }


    /**
	 * find the candidates for each party that are selected.
	 *
	 * @param ArrayList<int> take number of seats for each party as input
	 * @return return an arraylist of candidates that are selected
	 */

    public ArrayList<Candidate> insidePartyElection(ArrayList<Integer> numOfSeat){
        ArrayList<Candidate> selectedCandidate = new ArrayList<>();
        for (int i = 0; i < party.size(); i++){
            for(int chosenSeat = 0; chosenSeat < numOfSeat.get(i); chosenSeat++){
                selectedCandidate.add(findLargestCan(party.get(i).getMembers()));
            }
        }
        return selectedCandidate;
    }


    /**
	 * this method call the previous method to complete the OPL voting.
	 *
	 * @return return an arraylist of candidates as winner. 
	 */

    public ArrayList<Candidate> run_OPL(Scanner sc){

        // readBallot(sc);

        ArrayList<Integer> allocated  = updateSeat();

        while (checkRemainSeats() != 0){
            // find remaining votes
            ArrayList<Integer> remainVotes = remainingVotes(party);
            int largest = 0;
            boolean haveTie = false;
            int numOfTie = 0;
            ArrayList<Integer> indexOfTie = new ArrayList<>();
            // find the largest vote party and check for the tie condition
            for (int i: remainVotes){
                if (i > largest) {
                    indexOfTie.clear();
                    largest = i;
                    haveTie = false;
                    numOfTie = 0;
                    indexOfTie.add(i);
                } else if (i ==  largest){
                    haveTie = true;
                    numOfTie += 1;
                    indexOfTie.add(i);
                }
            }

            // if (haveTie) {
            //     // CoinFlip rand =  new CoinFlip(numOfTie)
            // } 

            int index = indexOfTie.get(0);
            allocated.set(index, allocated.get(index) + 1);
            Party largestParty = party.get(index);
            largestParty.setVote(-1);
        }

        ArrayList<Candidate> winner = insidePartyElection(allocated);

        return winner;
    }

}