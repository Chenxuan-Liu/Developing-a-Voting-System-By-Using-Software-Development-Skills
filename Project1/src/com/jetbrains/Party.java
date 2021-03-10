package com.jetbrains;

import java.util.ArrayList;

public class Party {
    public int vote;
    public String name;
//    public ArrayList<Candidate> members;

    public int getVote() {
        return vote;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Candidate> getMembers() {
        return members;
    }

    public Party(int vote, String name, ArrayList<Candidate> members){
        this.vote = vote;
        this.name = name;
        this.members = members;
    }
}
