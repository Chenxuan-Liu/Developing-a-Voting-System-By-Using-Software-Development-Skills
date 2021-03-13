import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the correctness of the functions in the IR_Ballot class.
 * This class has 1 private attribute.
 * @author Chenxuan Liu
 * @version 1.0
 */

public class IR_BallotTest {
    private IR_Ballot ballot = new IR_Ballot();
    //constructor has error. can not access voteRank.

    @Test
    public void getRank() {
        assertEquals(0, ballot.getRank());
    }

//    @Test
//    public void getRanksize() {
//        assertEquals(0, ballot.getRanksize());
//    }

    @Test
    public void updateRank() {
        ballot.updateRank();
        assertEquals(1, ballot.getRank());
    }
}