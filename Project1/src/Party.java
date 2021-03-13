import java.util.ArrayList;

/**
 * @author Zilong He
 * @version 1.0
 */

public class Party {
    /**
     * This class creates new Party to store information of all parties for voting system.
     * This class has 3 attributes.
     * Int vote is used to store the total number of votes that the party currently received.
     * String name is used to store the name of the party.
     * Candidate ArrayList is used to store all the candidates belonging to the party.
     * @return IOException.
     */
    public int vote;
    public String name;
    public ArrayList<Candidate> members;

    /**
     * This method is used to return party's currently received votes number.
     * @param args Unused.
     * @return Integer type. Return the number of received votes.
     */
    public int getVote() {
        return vote;
    }

    /**
     * This method is used to update the party's current received number of votes.
     * @param integer num of votes.
     * @return void.
     */
    public void setVote(int vote) {
        this.vote = vote;
    }

    /**
     * This method is used to get the name of the party.
     * @param args Unused.
     * @return String type. return the name of the party.
     */
    public String getName() {
        return name;
    }

    /**
     * This method is used to return the arraylist which contaons all the members of the party.
     * @param args Unused.
     * @return Candidate Arraylist. Return all members of the party.
     */
    public ArrayList<Candidate> getMembers() {
        return members;
    }

    /**
     * This method is used to add candidate to the party.
     * @param Candidate type, the candidate need to be added.
     * @return void.
     */
    public void addmember(Candidate candidate){
        this.members.add(candidate);
    }

    /**
     * This method creates new party instance.
     * @param string of party's name .
     */
    public Party(String name){
        this.vote = 0;
        this.name = name;
        this.members = new ArrayList<Candidate>();
    }
}
