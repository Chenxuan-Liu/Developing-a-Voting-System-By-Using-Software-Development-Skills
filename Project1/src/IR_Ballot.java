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
    if(currentRank >= voteRank.size()){
      return -1;
    }
    return voteRank.get(currentRank);
  }
  
  public void addRank(int rank){
    voteRank.add(rank);
  }
  
  public void setRank(int rank, int index){
    voteRank.set(index, rank);
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
