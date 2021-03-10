//package candidate;
//package com.journaldev.composition;
import java.util.ArrayList;
//import anotherpackage.anotherclass;
//import ir_ballot.IR_Ballot;

/**
* @author Yingwen Weng
* @version 1.0
*/

public class Candidate {
  /**
  * This class creates new candidate for voting system.
  * This class has 4 attibutes.
  * Int vote is used to store the number of votes currently received.
  * String name is used to store the name of the candidate.
  * String party is used to store the name of the candidat's party.
  * ArrayList ballot is used to store all the ballots curently recerived.
  * @return IOException.
  */
  private int vote;
  private String name;
  private Party party;
  private ArrayList<IR_Ballot> ballot;

  /**
  * This method creates new candidate instance.
  * @param string of candidates's name and Party of candidate's party .
  * @return IOException.
  */
  public Candidate(String name, Party p_party) {
    this.vote = 0;
    this.name = name;
    this.party = p_party;
    this.ballot = new ArrayList<IR_Ballot>();
  }

  /**
  * This method returns current recerived vote num of this candaites.
  * @param args Unused.
  * @return integer num.
  */
  public int getVote() {
    return vote;
  }

  /**
  * This method returns name of this candaites.
  * @param args Unused.
  * @return string.
  */
  public String getName() {
    return name;
  }

  /**
  * This method returns belonging party of this candaites.
  * @param args Unused.
  * @return Party.
  */
  public Party getParty() {
    return party;
  }

  /**
  * This method returns arraylist ballot.
  * @param args Unused.
  * @return IR_Ballot arraylist.
  */
  public ArrayList<IR_Ballot> getballots() {
    return ballot;
  }

}
