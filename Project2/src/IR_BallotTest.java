import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the correctness of the functions in the IR_Ballot class.
 * This class has 1 private attribute.
 * @author Chenxuan Liu
 * @version 1.0
 */

public class IR_BallotTest {
    private IR_Ballot ballot = new IR_Ballot(0);

    @Test
    public void getRank() {
        assertEquals(-1, ballot.getRank());
    }

    @Test
    public void addRank(){
        ballot.addRank(1);
        assertEquals(1, ballot.getRank());
    }

    @Test
    public void setRank(){
        ballot.addRank(100);
        ballot.addRank(2);
        ballot.setRank(1,0);
        assertEquals(1, ballot.getRank());
    }

    @Test
    public void updateRank() {
        ballot.addRank(1);
        ballot.addRank(2);
        ballot.updateRank();
        assertEquals(2, ballot.getRank());
    }
}