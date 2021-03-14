import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class OPL_sysTest {

    private Candidate can1 = new Candidate("c1","p1");
    private Candidate can2 = new Candidate("c2", "p1");
    private Candidate can3 = new Candidate("c3","p2");
    private Candidate can4 = new Candidate("c4","p2");
    private Party p1 = new Party("p1");
    private Party p2 = new Party("p2");

    private ArrayList<Party> partyList = new ArrayList<>();
    private ArrayList<Candidate> canList = new ArrayList<>();

    private Scanner sc = null;
    private PrintWriter pw;

    private OPL_sys opl1;
    private OPL_sys opl2;
//    @Test
//    public void readballot() {
//    }

    @Before
    public void setUp(){

        opl1 = new OPL_sys(canList, partyList, 3, 2,10,sc);

        can1.setVote(3);
        can2.setVote(2);
        can3.setVote(5);

        //      uodate candidate list
        canList.add(can1);
        canList.add(can2);
        canList.add(can3);

//      update party list
        partyList.add(p1);
        partyList.add(p2);


//      uodate candidate list
        canList.add(can1);
        canList.add(can2);
        canList.add(can3);

//      update party status
        p1.setVote(5);
        p2.setVote(5);
        p1.addmember(can1);
        p1.addmember(can2);
        p2.addmember(can3);

    }

    @Test
    public void firstround_Seats() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(1);

        assertEquals(expected, opl1.firstround_Seats());
    }

    @Test
    public void checkRemainSeats() {
        opl1.firstround_Seats();
        assertFalse(opl1.checkRemainSeats());


        can4.setVote(2);
        canList.add(can4);
        p2.setVote(7);
        p2.addmember(can4);

        opl2 = new OPL_sys(canList,partyList,4,3,12,sc);
        opl2.firstround_Seats();
        assertTrue(opl2.checkRemainSeats());
    }

    @Test
    public void findlargestvote() {
//      check for the coin flip condition
        assertTrue(opl1.findlargestvote()>=0);
        assertTrue(opl1.findlargestvote()<2);

//      check for the normal condition
        can4.setVote(2);
        canList.add(can4);
        p2.setVote(7);
        p2.addmember(can4);
        opl2 = new OPL_sys(canList,partyList,4,3,12,sc);

        assertEquals(1,opl2.findlargestvote());
    }

    @Test
    public void secondround_seats() {
        can4.setVote(2);
        canList.add(can4);
        p2.setVote(7);
        p2.addmember(can4);
        opl2 = new OPL_sys(canList,partyList,4,3,12,sc);

        ArrayList<Integer> input = opl2.firstround_Seats();
        assertTrue(opl2.checkRemainSeats());
        ArrayList<Integer> expect = new ArrayList<>();
        expect.add(1);
        expect.add(2);

        assertEquals(expect,opl2.secondround_seats(input));

    }

//    @Test
//    public void findwinnner() {
//        ArrayList<Integer> first = opl1.firstround_Seats();
//        ArrayList<Candidate> expect = new ArrayList<>();
//        expect.add(can3);
//        expect.add(can1);
//
//        assertEquals(expect, opl1.findwinnner(first));
//
//    }
}