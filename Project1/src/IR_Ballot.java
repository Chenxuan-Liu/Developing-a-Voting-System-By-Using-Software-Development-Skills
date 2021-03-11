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
  * ArrayList voteRank is used to store the rank of all ballots in the election.
  * Int currentRank is used to store current rank of the IR ballot.
  */
  private ArrayList<Integer> voteRank;
  private int currentRank;

  /**
  * This method creates new IR_Ballot instance.
  * @param args Unused.
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

  /**
  * This method add one rank to voteRank arraylist.
  * @param integer num.
  * @return void.
  */
  public void addRank(int rank){
    voteRank.add(rank);
  }

  /**
  * This method set integer num at the specific location in arraylist voteBank.
  * @param 2 integer nums.
  * @return void.
  */
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
