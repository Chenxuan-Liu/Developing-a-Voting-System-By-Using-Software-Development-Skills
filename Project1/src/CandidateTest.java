import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Chenxuan Liu
 * @version 1.0
 */

public class CandidateTest {
    /**
     * This class tests the correctness of the functions in the Candidate class.
     * This class has 5 private attributes.
     * @return IOException.
     */
    private ArrayList<Candidate> candidates = new ArrayList<>();
    private ArrayList<Candidate> emptyCandidate = new ArrayList<>();
    private Candidate candidate1 = new Candidate("Jack", null);
    private Party p1 = new Party("p1");
    private Candidate candidate2 = new Candidate("Jerry", "p1");


    @Before
    public void setUp() {
        candidates.add(candidate1);
    }

    @Test
    public void getVote() {
        assertEquals(0, candidate1.getVote());
    }

    @Test
    public void setVote() {
        candidate1.setVote(1);
        assertEquals(1, candidate1.getVote());
        candidate1.setVote(-1);
        assertEquals(-1, candidate1.getVote());
        candidate1.setVote(0);
        assertEquals(0,candidate1.getVote());
    }

    @Test
    public void getName() {
        assertEquals("Jack", candidate1.getName());
        assertEquals("Jerry", candidate2.getName());
    }

    @Test
    public void getParty() {
        assertEquals("p1", candidate2.getParty());
    }

    @Test
    public void getballots() {
        ArrayList<IR_Ballot> ballot = new ArrayList<>();
        IR_Ballot ball = new IR_Ballot();
        ball.addRank(0);
        ballot.add(ball);
        candidate2.addIRballot(ballot.get(0));
        assertEquals(ballot, candidate2.getballots());
    }

    @Test
    public void addIRballot(){
        ArrayList<IR_Ballot> ballot = new ArrayList<>();
        IR_Ballot ball1 = new IR_Ballot();
        IR_Ballot ball2 = new IR_Ballot();
        ball1.addRank(0);
        ball1.addRank(1);
        ball2.addRank(0);
        ballot.add(ball1);
        ballot.add(ball2);
        candidate2.addIRballot(ball1);
        candidate2.addIRballot(ball2);
        assertEquals(ballot, candidate2.getballots());
    }

}