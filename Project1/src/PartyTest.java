import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Chenxuan Liu
 * @version 1.0
 */

public class PartyTest {
    /**
     * This class tests the correctness of the functions in the Party class.
     * This class has 2 private attributes.
     * @return IOException.
     */
    private ArrayList<Candidate> can = new ArrayList<>();
    private Party p1 = new Party("p1");


    @Test
    public void getVote() {
        assertEquals(0, p1.getVote());
    }

    @Test
    public void setVote() {
        p1.setVote(100);
        assertEquals(100, p1.getVote());
        p1.setVote(-1);
        assertEquals(-1, p1.getVote());
    }

    @Test
    public void getName() {
        assertEquals("p1", p1.getName());
    }

    @Test
    public void getMembers() {
        assertEquals(null, p1.getMembers());
        can.add(new Candidate("c1", "p1"));
        assertEquals(can, p1.getMembers());
    }
}