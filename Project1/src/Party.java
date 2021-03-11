import java.util.ArrayList;

public class Party {
    public int vote;
    public String name;
    public ArrayList<Candidate> members;

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Candidate> getMembers() {
        return members;
    }
    
    public void addmember(Candidate candidate){
        this.members.add(candidate);
    }

    public Party(String name){
        this.vote = 0;
        this.name = name;
        this.members = new ArrayList<Candidate>();
    }
}
