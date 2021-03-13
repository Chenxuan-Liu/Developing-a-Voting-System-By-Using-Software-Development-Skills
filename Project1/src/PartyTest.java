import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests the correctness of the functions in the Party class.
 * This class has 2 private attributes.
 * @author Chenxuan Liu
 * @version 1.0
 */

public class PartyTest {

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
    public void addMember(){
        Candidate c1 = new Candidate("c1", "p1");
        Candidate c2 = new Candidate("c2", "p1");
        can.add(c1);
        can.add(c2);
        p1.addmember(c1);
        p1.addmember(c2);
        assertEquals(can, p1.getMembers());
    }

    @Test
    public void getMembers() {
        assertEquals(can, p1.getMembers());
        Candidate c1 = new Candidate("c1", "p1");
        can.add(c1);
        p1.addmember(c1);
        assertEquals(can, p1.getMembers());
    }
}