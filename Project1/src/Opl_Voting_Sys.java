/*
* @author Chenxuan Liu
*/

import java.io.*;  
import java.util.Scanner;
import java.util.ArrayList;


/**
 * Opl_Voting_Sys is a class that creat for open party list ballot voting type.
 * It must need two other class, which is party and candidate to complete the task.
 * 
 *
 * Author: Chenxuan Liu
 */

public class Opl_Voting_Sys{
    private ArrayList<Candidate> candidate;
    private ArrayList<Party> party;
    private int number_candidate;
    private int num_seats;
    private int total_ballot;
    private Coin_Flip coin = new Coin_Flip();

    public Opl_Voting_Sys(ArrayList<Candidate> candidate, ArrayList<Party> party, int number_candidate, int num_seats, int total_ballot){
        this.candidate = candidate;
        this.party = party;
        this.number_candidate = number_candidate;
        this.num_seats = num_seats;
        this.total_ballot = total_ballot;
    }

    /**
	 * read in the csv file and generate a file pointer
	 *
	 * @param String the file name
	 * @return returna scanner pointer and set delimiter as ","
	 */
    // public static Scanner readFile(String input_file){
    //     //parsing a CSV file into Scanner class constructor 
    //     Scanner sc = new Scanner(new File(input_file));

    //     sc.useDelimiter(",");   //sets the delimiter pattern
    //     return sc;
    // }

    /**
	 * set up the basic information of the OPL voting system
	 *
	 * @param Scanner a scanner pointer help to read the file
	 * 
	 */

    // public void setUp(Scanner sc){
    //     // treat 1 as IR_Voting Sys
    //     // treat 0 as OPL_Voting Sys
    //     if (sc.next() == 1){
    //         System.exit(0);
    //     }
        
    //     number_candidate = sc.next()

    //     /*
    //     * update the candidate list
    //     */
    //     while(sc.hasNext()){
    //         can_name = sc.next();
    //         can_party = sc.next();
    //         candidate.add(new Candidate(can_name, can_party));
    //         if (!party.contains(new Party(can_party))){
    //             party.add(new Party(can_name));
    //         }
    //     }

    //     if (number_candidate != candidate.size()) {
    //         System.out.println("The number of candidates does not match the candidates")
    //         System.exit(0)
    //     }

    //     num_seats = sc.next();
    //     total_ballot = sc.next();
    // }


    /**
	 * Read each ballot individually and increase the vote count for both candidate and party object
	 *
	 * @param Scanner a scanner pointer help to read the file
     * 
	 */

    // public void readBallot(Scanner sc){
    //     while (sc.hasNextLine()){
            
    //         int index = 0;
    //         sc.useDelimiter(",");
    //         String ballot = sc.nextLine();
            

    //         Candidate voted_candidate = candidate.get(ballot);

    //         // candidaate class need to add a new function set_vote
    //         voted_candidate.set_vote(voted_candidate.get_vote() + 1);

    //         // change the class of party from string to Party
    //         Party voted_party = voted_candidate.getParty();
    //         // party class need to add a new function set_vote
    //         voted_party.set_vote(voted_party.get_vote() + 1);
    //     }
    // }

    /**
	 * Find the quota for the OPL_Voting_Sys
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