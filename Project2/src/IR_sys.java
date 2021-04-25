import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.lang.*;
import java.util.*;
import javax.sound.midi.*;

/**
 * This class is used for IR vote, stores all required information of IR vote.
 * And perform the vote counting.
 * <p></p>
 * It first create IR_Ballot objects for each ballot. Allocate the ballot to candidates and check the majority.
 * Discard the candidate with least votes and redistribute the ballot.
 * It stops when someone reach the majority. If only two candidates remain, the popularity wins.
 * @author Jicheng Zhu
 * @version 1.0
 */

public class IR_sys{

	private ArrayList<Candidate> candidates;
	private ArrayList<Party> parties;
	private int num_candidate, num_seats, total_ballot;
	private Coin_Flip coin = new Coin_Flip();
	Scanner scanner;
	private PrintWriter mywriter;

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
				  int number_candidate, int num_seats, int total_ballot, Scanner scanner, PrintWriter mywriter){
		this.candidates = candidate;
		this.parties = party;
		this.num_candidate = number_candidate;
		this.num_seats = num_seats;
		this.total_ballot = total_ballot;
		this.scanner = scanner;
		this.mywriter = mywriter;
	}

	/**
	* Reads and stores ballots from the CSV file.
	* @param scanner java scanner type used to help reading input file.
	* @return void.
	* @exception no exception.
	*/
	public void readballot(Scanner scanner){
		IR_Ballot ballot;
		int index = 1;
			while (scanner.hasNextLine()) {
				ballot = new IR_Ballot(index);
				mywriter.printf("read No.%d ballot:",index);
				getRecordFromLine(" "+scanner.nextLine(), ballot);
				Candidate candidate = candidates.get(ballot.getRank() - 1);
				candidate.addIRballot(ballot);
				mywriter.printf("%s from party %s get No.%d ballot, he(she) has %d vote(s) now%n",candidate.getName(),candidate.getParty(),index,candidate.getVote());
				index++;
			}
		mywriter.flush();
	}

	/**
	* Gets rank from the line of the file and store the rank in the system.
	* @param line a line of input CSV file, used to get rank.
	* @param ballot the ballot which is used to store rank.
	* @return void.
	* @exception no exception.
	*/
	private void getRecordFromLine(String line, IR_Ballot ballot) {
		List<String> values = new ArrayList<String>();
		int invalid_ballot = 0;
		
		try (Scanner rowScanner = new Scanner(line)) {
			mywriter.printf("%s%n",line);
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext()) {
				String value = rowScanner.next();
				if(!value.trim().isEmpty()){
					values.add(value);
					ballot.addRank(1); //only used to initializa the size
				} else {
					values.add("-1");
					invalid_ballot++;
				}
			}
		}

		// Check for the invalid ballots situation, if this ballot is invalid, set all the rand to -1
		if (invalid_ballot > candidates.size()/2 || invalid_ballot > (candidates.size() + 1)/2){
			for(int i = 1; i <= values.size(); i++){
				if(value != -1){
					ballot.setRank(i, -1);
				}
			}
		}else{
			//set the correct rank
			for(int i = 1; i <= values.size(); i++){
				int value = Integer.parseInt(values.get(i-1).trim());
				if(value != -1){
					ballot.setRank(i, value - 1);
				}
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
		if (num_candidate < 1){
			System.out.println("ERROR: No candidate in the file.");
			mywriter.println("ERROR: No candidate in the file");
			mywriter.close();
			System.exit(1);
		} else if (num_candidate == 1){
			Candidate candidate = candidates.get(0);
			mywriter.printf("%s from party %s get %d votes, he(she) wins.%n",candidate.getName(),candidate.getParty(),candidate.getVote());
			mywriter.flush();
			return candidate;
		} else if (num_candidate == 2){
			ArrayList<Candidate> candidates = new ArrayList<Candidate>();
			for(Candidate c:this.candidates){
				if(c.isvalid()){
					candidates.add(c);
				}
			}
			
			Candidate candidate0 = candidates.get(0);
			Candidate candidate1 = candidates.get(1);
			int vote0 = candidate0.getVote();
			int vote1 = candidate1.getVote();
			mywriter.println("only two candidates left.");
			mywriter.printf("%s from %s has %d votes.%n",candidate0.getName(),candidate0.getParty(),candidate0.getVote());
			mywriter.printf("%s from %s has %d votes.%n",candidate1.getName(),candidate1.getParty(),candidate1.getVote());
			if (vote0 > vote1){
				mywriter.printf("%s from %s has more votes, he(she) wins.%n",candidate0.getName(),candidate0.getParty());
				mywriter.flush();
				return candidate0;
			} else if (vote1 > vote0){
				mywriter.printf("%s from %s has more votes, he(she) wins.%n",candidate1.getName(),candidate1.getParty());
				mywriter.flush();
				return candidate1;
			} else{
				mywriter.println("Two candidates have the same vote, start a coin flip.");
				Candidate winner = candidates.get(coin.flip(2));
				mywriter.printf("%s from %s wins.%n",winner.getName(),winner.getParty());
				mywriter.flush();
				return winner;
			}
		}

		for(int i = 0; i < candidates.size(); i ++){
			if(candidates.get(i).isvalid()){
				int vote = candidates.get(i).getVote();
				if(vote > total_ballot/2){
					Candidate winner = candidates.get(i);
					mywriter.printf("a majority needs more than %d votes.%n",total_ballot/2);
					mywriter.printf("%s from %s has %d votes, he(she) wins.%n",winner.getName(),winner.getParty(),winner.getVote());
					mywriter.flush();
					return winner;
				}
			}
		}
		return null;
	}

	/**
	* Finds the candidate has the least ballots, if find, return the candidate.
	* @param args Unused.
	* @return return the index of the candidate with the least ballots.
	* @exception no exception.
	*/
	private int get_leastcandidate(){
		mywriter.println("Finding the candidate with the least votes.");
		int numofTie = 1;
		int index = 0;
		ArrayList<Integer> Tielist = new ArrayList<>();
		Tielist.add(index);
		int least_vote = candidates.get(index).getVote();
		
		while(!candidates.get(index).isvalid()){
			index++;
			Tielist.clear();
			Tielist.add(index);
		}
		
		least_vote = candidates.get(index).getVote();

		for(int i = index + 1; i < candidates.size(); i ++){
			if(candidates.get(i).isvalid()){
				int vote = candidates.get(i).getVote();
				if(vote < least_vote){
					index = i;
					numofTie = 1;
					Tielist.clear();
					Tielist.add(i);
					least_vote = vote;
				} else if(vote == least_vote){
					numofTie++;
					Tielist.add(i);
				}
			}
		}
		
		if(numofTie > 1){
			for(int i:Tielist){
				mywriter.printf("%s from %s ",candidates.get(i).getName(),candidates.get(i).getParty());
			}
			mywriter.println("have the same votes. start a coin flip.");
			index = Tielist.get(coin.flip(numofTie));
			mywriter.printf("%s from %s is chose by a coin flip with least votes.%n",candidates.get(index).getName(),candidates.get(index).getParty());
		} else{
			mywriter.printf("%s from %s has least votes.%n",candidates.get(index).getName(),candidates.get(index).getParty());
		}
		mywriter.flush();
		return index;
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
		mywriter.println("start redistribution.");
		for(int i = 0; i < ballots.size(); i++){
			ballot = ballots.get(i);
			mywriter.printf("redistribute No.%d ballot from candidate %s%n",ballot.getindex(), candidate.getName());
			ballot.updateRank();
			if(ballot.getRank() == -1){
				mywriter.printf("No remaining rank, discard No.%d ballot.%n",ballot.getindex());
				continue;
			}
			Candidate receive = candidates.get(ballot.getRank() - 1);
			//if next candidate is not valid
			while(!receive.isvalid()){
				mywriter.printf("%s from %s is already removed, get next rank.%n",receive.getName(),receive.getParty());
				ballot.updateRank();
				if(ballot.getRank() == -1){
					break;
				} else {
					receive = candidates.get(ballot.getRank() - 1);
				}
			}
			//if no remaining candidates in the ballot
			if (ballot.getRank() != -1){
					mywriter.printf("%s from %s receive this ballot.%n",receive.getName(),receive.getParty());
					receive.addIRballot(ballot);
					mywriter.printf("%s has %d votes now%n",receive.getName(),receive.getVote());
			} else {
				mywriter.printf("No remaining rank, discard No.%d ballot.%n",ballot.getindex());
			}
		}
		mywriter.printf("remove candidate %s from %s.%n",candidate.getName(),candidate.getParty());
		
		candidate.discard();
		num_candidate--;
		mywriter.printf("%d candidates remain.%n",num_candidate);
		mywriter.flush();
	}
	
}
