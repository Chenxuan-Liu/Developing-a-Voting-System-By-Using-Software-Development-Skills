
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class IR_sysTest {
    private Candidate can1 = new Candidate("c1","p1");
    private Candidate can2 = new Candidate("c2", "p1");
    private Candidate can3 = new Candidate("c3","p2");
    private Party p1 = new Party("p1");
    private Party p2 = new Party("p2");

    private ArrayList<Party> partyList = new ArrayList<>();
    private ArrayList<Candidate> canList = new ArrayList<>();

//    private File tempFile = new File("./csvfile/IR_direct_winner.csv");
//    private Scanner sc = new Scanner(tempFile);
    private Scanner sc = null;
    private PrintWriter pw;

    private IR_sys ir;

//    create IR ballots
    private IR_Ballot ballot1 = new IR_Ballot(0);
    private IR_Ballot ballot2 = new IR_Ballot(1);
    private IR_Ballot ballot3 = new IR_Ballot(2);
    private IR_Ballot ballot4 = new IR_Ballot(3);
    private IR_Ballot ballot5 = new IR_Ballot(4);
    private IR_Ballot ballot6 = new IR_Ballot(5);

    @Before
    public void setUp() throws FileNotFoundException {
        pw = new PrintWriter("IR_audit.txt");

        ir = new IR_sys(canList, partyList, 3, 2, 5, sc, pw);

//      set ballot rank
//      ballot1 2,1,3
        ballot1.addRank(2);
        ballot1.addRank(1);
        ballot1.addRank(3);

//      ballot2 1,2,3
        ballot2.addRank(1);
        ballot2.addRank(2);
        ballot2.addRank(3);

//      ballot3 2,1,3
        ballot3.addRank(2);
        ballot3.addRank(1);
        ballot3.addRank(3);

//      ballot4 1,3,2
        ballot4.addRank(3);
        ballot4.addRank(1);
        ballot4.addRank(2);

//      ballot5 2,1,3
        ballot5.addRank(2);
        ballot5.addRank(1);
        ballot5.addRank(3);

//      ballot6 3,1,2;
//        ballot6.addRank(3);
//        ballot6.addRank(1);
//        ballot6.addRank(2);

//      update candidate ir ballot
        can1.addIRballot(ballot2);
        can2.addIRballot(ballot1);
        can2.addIRballot(ballot3);
        can2.addIRballot(ballot5);
        can1.addIRballot(ballot4);
//        can3.addIRballot(ballot6);

//      uodate candidate list
        canList.add(can1);
        canList.add(can2);
        canList.add(can3);

//      update party list
        partyList.add(p1);
        partyList.add(p2);

    }

//    @Test
//    public void readballot() {
//        ir.readballot(sc);
//    }

    @Test
    public void haswinner() {
        assertEquals(can2, ir.haswinner());
    }

//    @Test
//    public void get_leastcandidate() {
//        assertEquals(2, ir.get_leastcandidate());
//    }

    @Test
    public void redistribution() {
        ir.redistribution();
        assertEquals(3,can2.getVote());
        assertEquals(2,can1.getVote());
    }
}
