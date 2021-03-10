//package ir_ballot;
import java.util.ArrayList;

/**
* @author Yingwen Weng
* @version 1.0
*/
public class IR_Ballot {
  /**
  * This class created to produce ballots for the voting system.
  * This class has 2 attibutes.
  * ArrayList voteRank is used to ...
  * Int currentRank is used to ..
  */
  private ArrayList<Integer> voteRank;
  private int currentRank;

  /**
  * This method creates new IR_Ballot instance.
  * @param args Unused.
  * @return IOException.
  */
  public IR_Ballot() {
    voteRank = new ArrayList<Integer>();
    currentRank = 0;
  }

  /**
  * This method returns current rank of ballots.
  * @param args Unused.
  * @return num.
  */
  public int getRank() {
    return currentRank;
  }

  /**
  * This method returns ranksize.
  * @param args Unused.
  * @return integer num.
  */
  public int getRanksize() {
    return voteRank.size();
  }

  /**
  * This method updates ballot's current rank.
  * @param args Unused.
  * @return Void.
  */
  public void updateRank() {
    currentRank = currentRank + 1;
  }
  }
