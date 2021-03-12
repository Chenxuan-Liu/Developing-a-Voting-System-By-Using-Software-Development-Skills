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
    private Candidate candidate2 = new Candidate("Jerry", p1);


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
        assertEquals(p1, candidate2.getParty());
        assertEquals(null, candidate2.getParty());
    }

//    need to fix the Voting system class and IR voting system
//    @Test
//    public void getballots() {
//
//    }
}