import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.lang.*;
import java.util.*;

public class IR_sys{
	private ArrayList<Candidate> candidates;
	private ArrayList<Party> parties;
	int num_candidate, num_seats, total_ballot;
	private Coin_Flip coin = new Coin_Flip();
	Scanner scanner;
	
	public IR_sys(ArrayList<Candidate> candidate, ArrayList<Party> party, int number_candidate, int num_seats, int total_ballot, Scanner scanner){
		this.candidates = candidate;
		this.parties = party;
		this.num_candidate = number_candidate;
		this.num_seats = num_seats;
		this.total_ballot = total_ballot;
		this.scanner = scanner;
	}
	
	public void readballot(Scanner scanner){
		IR_Ballot ballot;
			while (scanner.hasNextLine()) {
				ballot = new IR_Ballot();
				getRecordFromLine(scanner.nextLine(), ballot);
				candidates.get(ballot.getRank() - 1).addIRballot(ballot);
			}
	}
	
	private static void getRecordFromLine(String line, IR_Ballot ballot) {
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());
				ballot.addRank(1); //only used to initializa the size
			}
		}
		//set the correct rank
		for(int i = 0; i < values.size(); i++){
			ballot.setRank(i, Integer.parseInt(values.get(i)));
		}
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
				return candidates.get(coin.flip(2));
			}
		}
		
		for(int i = 0; i < candidates.size(); i ++){
			int vote = candidates.get(i).getVote();
			if(vote > total_ballot/2){
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
				least_candidate = random[coin.flip(2)];
				least_vote = candidates.get(least_candidate).getVote();
			}
		}
		return least_candidate;
	}
	
	public void redistribution(){
		int least = get_leastcandidate();
		Candidate candidate = candidates.get(least);
		ArrayList<IR_Ballot> ballots = candidate.getballots();
		IR_Ballot ballot;
		
		//ballots redistribution
		for(int i = 0; i < ballots.size(); i++){
			ballot = ballots.get(i);
			ballot.updateRank();
			if (ballot.getRank() != -1){
				candidates.get(ballot.getRank() - 1).addIRballot(ballot);
			}
		}
		
		candidates.remove(least);
	}
	
}