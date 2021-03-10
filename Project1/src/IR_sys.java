//merge test
import java.util.ArrayList;
import java.io.*;
import java.lang.*;

public class IR_sys extends Voting_Sys{
	private ArrayList<Candidate> candidates;
	private ArrayList<Party> parties;
	
	public void readballot(){
		//placeholer for file parsing
	}
	
	public Candidate haswinner(){
		if (candidates.size() < 1){
			System.out.println("ERROR: No candidate in the file.");
			System.exit(1);
		} else if (candidates.size() == 1){
			return candidates.get(0);
		} else if (candidates.size() == 2){
			int vote0 = candidates.get(0).getVote();
			int vote1 = candidates.get(1).getVote();
			if (vote0 > vote1){
				return candidates.get(0);
			} else if (vote1 > vote0){
				return candidates.get(1);
			} else{
				return candidates.get(coinflip(2));
			}
		}
		
		for(int i = 0; i < candidates.size(); i ++){
			int vote = candidates.get(i).getVote();
			if(vote > totalballot/2){
				return candidates.get(i);
			}
		}
		return null;
	}
	
	public int get_leastcandidate(){
		int least_vote = candidates.get(0).getVote();
		int least_candidate = 0;
		
		for(int i = 0; i < candidates.size(); i ++){
			int vote = candidates.get(i).getVote();
			if(vote < least_vote){
				least_candidate = i;
				least_vote = vote;
			} else if(vote == least_vote){
				int[] random = {i,least_candidate};
				least_candidate = random[coinflip(2)];
				least_vote = candidates.get(least_candidate).getVote();
			}
		}
		return least_candidate;
	}
	
	public void redistribution(){
		int least = get_leastcandidate();
		Candidate candidate = candidates.get(least);
		ArrayList<Ballot> ballots = candidate.getBallots();
		Ballot ballot;
		
		//ballots redistribution
		for(int i = 0; i < ballots.size(); i++){
			ballot = ballots.get(i);
			ballot.updateRank();
			if (ballot.getRank() < ballot.getRanksize()){
				//
				//candidates.get()
			}
		}
		
		candidates.remove(least);
		return null;
		
	}
	
}