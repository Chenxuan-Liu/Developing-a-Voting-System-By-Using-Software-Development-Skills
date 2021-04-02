import java.util.ArrayList;

/**
 * This class creates new Party to store information of all parties for voting system.
 * This class has 3 attributes.
 * Int vote is used to store the total number of votes that the party currently received.
 * String name is used to store the name of the party.
 * Candidate ArrayList is used to store all the candidates belonging to the party.
 *
 * @return IOException.
 * @author Zilong He
 * @version 1.0
 */

public class Party {

    public int vote;
    public String name;
    public ArrayList<Candidate> members;

    /**
     * Returns currently received votes number of this party.
     * @param args Unused.
     * @return return the number of received votes.
     * @exception no exception.
     */
    public int getVote() {
        return vote;
    }

    /**
     * Updates this party's current received number of votes with the new number.
     * @param vote number of votes to be allocated to the party.
     * @return void.
     * @exception no exception.
     */
    public void setVote(int vote) {
        this.vote = vote;
    }

    /**
     * Gets the name of this party.
     * @param args Unused.
     * @return return the name of this party.
     * @exception no exception.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the arraylist which contains all the members of the party.
     * @param args Unused.
     * @return return all members of the party.
     * @exception no exception.
     */
    public ArrayList<Candidate> getMembers() {
        return members;
    }

    /**
     * Adds new candidate to the party.
     * @param candidate, Candidate type, the candidate needs to be added.
     * @return void.
     * @exception no exception.
     */
    public void addmember(Candidate candidate){
        this.members.add(candidate);
    }

    /**
     * Constructor, creates new party instance.
     * @param name, string of party's name.
     * @exception no exception.
     */
    public Party(String name){
        this.vote = 0;
        this.name = name;
        this.members = new ArrayList<Candidate>();
    }
}
