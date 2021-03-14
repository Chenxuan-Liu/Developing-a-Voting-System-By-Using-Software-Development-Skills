//package candidate;
//package com.journaldev.composition;
import java.util.ArrayList;
import java.io.*;

//import anotherpackage.anotherclass;
//import ir_ballot.IR_Ballot;

/**
 * Stores all the information of each candidates for voting system.
 * Provides function operations to get and modify data in the Candidate class.
 * <p>
 * Uses vote (int type) to store the total number of votes that the candidate currently received.
 * Uses name (string type) to store the name of the candidate.
 * Uses party (string type) to store the name of the candidate's party.
 * IR_Ballot ArrayList ballot is used to store all the ballots currently received.
 * @author Yingwen Weng
 * @version 1.0
 */

public class Candidate {

  private int vote;
  private String name;
  private String party;
  private boolean valid;
  private ArrayList<IR_Ballot> ballot;

  /**
  * Constructor, creates new candidate instance.
  * @param name the name of the newly generated candidate.
  * @param party the party name of the newly generated candidate.
  * @exception no exception.
  */
  public Candidate(String name, String party) {
    this.vote = 0;
    this.valid = true;
    this.name = name;
    this.party = party;
    this.ballot = new ArrayList<IR_Ballot>();
  }

  /**
  * Returns current received vote num of this candidates.
  * @param args Unused.
  * @return candidate received number of votes.
  * @exception no exception.
  */
  public int getVote() {
    return vote;
  }

  /**
  * Receives new integer number to change the value of vote.
  * @param num the new number of votes to be set for the candidate.
  * @return void.
  * @exception no exception.
  */
  public void setVote(int v) {
    vote = v;
  }

  /**
  * Returns name of this candidate.
  * @param args Unused.
  * @return name of this candidate.
  * @exception no exception.
  */
  public String getName() {
    return name;
  }

  /**
  * Returns belonging party of this candidate.
  * @param args Unused.
  * @return belonging party name of this candidate.
  * @exception no exception.
  */
  public String getParty() {
    return party;
  }

  /**
  * Returns arraylist ballot which contains this candidate received all ballots.
  * @param args Unused.
  * @return an arraylist contains all IR ballots.
  * @exception no exception.
  */
  public ArrayList<IR_Ballot> getballots() {
    return ballot;
  }

  /**
  * Adds one IR ballot to this candidate.
  * @param ballot new ballot need to be added to the candidate.
  * @return void.
  * @exception no exception.
  */
  public void addIRballot(IR_Ballot ballot){
    this.vote = this.vote + 1;
    this.ballot.add(ballot);
  }
  
  public void discard(){
    valid = false;
  }
  
  public boolean isvalid(){
    return valid;
  }

}
