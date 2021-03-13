import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * This class tests the correctness of the functions in the IR_sys class.
 * This class has 5 private attributes.
 * @author Chenxuan Liu
 * @version 1.0
 */

public class IR_sysTest {
    private Candidate can1 = new Candidate("c1","p1");
    private Candidate can2 = new Candidate("c2", "p1");
    private Candidate can3 = new Candidate("c3","p2");
    private Party p1 = new Party("p1");
    private Party p2 = new Party("p2");

    private ArrayList<Party> partyList = new ArrayList<>();
    private ArrayList<Candidate> canList = new ArrayList<>();

    private Scanner sc = new Scanner();

    private IR_sys ir = new IR_sys(canList, partyList, 3, 2, 10, sc);

    @Before
    public void setUp() throws Exception {
        can1.setVote(3);
        can2.setVote(3);
        can3.setVote(4);

        canList.add(can1);
        canList.add(can2);
        canList.add(can3);

        partyList.add(p1);
        partyList.add(p2);

    }

    @Test
    public void readballot() {
        ir.readballot(sc);
    }

    @Test
    public void haswinner() {

    }

    @Test
    public void get_leastcandidate() {
    }

    @Test
    public void redistribution() {
    }
}