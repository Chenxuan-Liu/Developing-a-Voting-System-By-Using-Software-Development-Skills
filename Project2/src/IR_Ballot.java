//package ir_ballot;
import java.util.ArrayList;

/**
 * This class created to produce ballots for the voting system.
 * Contains all the information of an IR Ballot and all the functions
 * needed to obtain and modify the private data of an IR Ballot.
 * <p></p>
 * This class contains 2 attributes.
 * ArrayList voteRank is used to store the rank of all ballots in the election.
 * Int currentRank is used to store current rank of the IR ballot.
 * @author Yingwen Weng
 * @version 1.0
 */
public class IR_Ballot {

  private ArrayList<Integer> voteRank;
  private int currentRank;
  private int index;

  /**
  * Constructor, creates new IR_Ballot instance.
  * @param index the index of the ballot.
  * @exception no exception.
  */
  public IR_Ballot(int index) {
    voteRank = new ArrayList<Integer>();
    currentRank = 0;
    this.index = index;
  }

  /**
  * Returns current rank of ballot.
  * @param args Unused.
  * @return current rank of the IR ballot.
  * @exception no exception.
  */
  public int getRank() {
    if(currentRank >= voteRank.size()){
      return -1;
    }
    return voteRank.get(currentRank);
  }

  /**
  * Adds one rank to IR ballot's current voteRank arraylist.
  * @param rank new rank values to be added to IR ballot.
  * @return void.
  * @exception no exception.
  */
  public void addRank(int rank){
    voteRank.add(rank);
  }

  /**
  * Sets new rank at specific location in arraylist voteBank.
  * @param rank new rank values to be added to IR ballot.
  * @param index  index of position to insert the new rank.
  * @return void.
  * @exception no exception.
  */
  public void setRank(int rank, int index){
    voteRank.set(index, rank);
  }


  /**
  * Updates ballot's current rank by one,
  * ballot votes for the candidate of the next order.
  * @param args Unused.
  * @return void.
  * @exception no exception.
  */
  public void updateRank() {
    currentRank = currentRank + 1;
  }
  
  /**
  * return the index of the ballot
  * @param args Unused.
  * @return the index of the ballot
  * @exception no exception.
  */
  public int getindex(){
    return this.index;
  }
}
