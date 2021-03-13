import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * This class is used for IR vote, stores all required information of IR vote.
 * <p></p>
 *
 * @author Jicheng Zhu
 * @version 1.0
 */

public class IR_sys{

	private ArrayList<Candidate> candidates;
	private ArrayList<Party> parties;
	int num_candidate, num_seats, total_ballot;
	private Coin_Flip coin = new Coin_Flip();
	Scanner scanner;

	/**
	* Constructor, creates new IR_sys instance.
	* @param candidate Candidate ArrayList, which contains all candidates to join the IR vote.
	* @param party Party ArrayList which contains all parties that have joined the IR vote.
	* @param number_candidate total number of all candidates participating in the IR election.
	* @param num_seats total number of winning seats in the IR election.
	* @param total_ballot total number of ballot in the IR election.
	* @param scanner java scanner type used to help reading the input CSV file.
	*/
	public IR_sys(ArrayList<Candidate> candidate, ArrayList<Party> party,
				  int number_candidate, int num_seats, int total_ballot, Scanner scanner){
		this.candidates = candidate;
		this.parties = party;
		this.num_candidate = number_candidate;
		this.num_seats = num_seats;
		this.total_ballot = total_ballot;
		this.scanner = scanner;
	}

	/**
	* Reads and stores ballots from the CSV file.
	* @param scanner java scanner type used to help reading input file.
	* @return void.
	* @exception no exception.
	*/
	public void readballot(Scanner scanner){
		IR_Ballot ballot;
			while (scanner.hasNextLine()) {
				ballot = new IR_Ballot();
				getRecordFromLine(" "+scanner.nextLine(), ballot);
				
				///System.out.println(ballot.getRank());
				candidates.get(ballot.getRank() - 1).addIRballot(ballot);
//				for(Candidate c:candidates){
//					System.out.println(c.getName());
//					System.out.println(c.getVote());
//				}
			}
	}

	/**
	* Gets rank from the line of the file and store the rank in the system.
	* @param line a line of input CSV file, used to get rank.
	* @param ballot the ballot which is used to store rank.
	* @return void.
	* @exception no exception.
	*/
	private static void getRecordFromLine(String line, IR_Ballot ballot) {
		List<String> values = new ArrayList<String>();
		
		System.out.println(line);
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext()) {
				String value = rowScanner.next();
				if(!value.trim().isEmpty()){
					values.add(value);
					ballot.addRank(1); //only used to initializa the size
				} else {
					values.add("-1");
				}
			}
		}
		System.out.println(values);
		//set the correct rank
		for(int i = 1; i <= values.size(); i++){
			int value = Integer.parseInt(values.get(i-1).trim());
			if(value != -1){
				ballot.setRank(i, value - 1);
			}
		}
	}

	/**
	* Determines whether a winner exists in the election, if yes, return the candidate.
	* @param args Unused.
	* @return return the winner candidate.
	* @exception no exception.
	*/
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

	/**
	* Finds the candidate has the least ballots, if find, return the candidate.
	* @param args Unused.
	* @return return the candidate with the least ballots.
	* @exception no exception.
	*/
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
			}
		}
		return least_candidate;
	}

	/**
	* Redistributes the ballots of the candidate with the least ballots to other candidates.
	* @param args Unused.
	* @return void.
	* @exception no exception.
	*/
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
