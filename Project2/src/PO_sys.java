import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.lang.*;
import java.util.*;
import javax.sound.midi.*;


/**
* This class is used for PO voting.
* OPL_sys is a class that creat for popularity only ballot voting type.
* It must need two other class, which is party and candidate to complete the task.
* Read the ballot and allocate it to its candidate.
* The person with most votes wins. For any tie in the vote count, a coin flip is used to settle the tie.
* <p></p>
* This class has 6 attributes.
*
* @author Yingwen Weng
* @version 1.0
*/

public class PO_sys{

	private ArrayList<Candidate> candidates;
	private ArrayList<Party> parties;
	private int num_candidate, num_seats, total_ballot;
	private Coin_Flip coin = new Coin_Flip();
	private PrintWriter mywriter;

	/**
	* Constructor, creates new PO_sys instance.
	* @param candidate Candidate ArrayList which contains all the candidates in the election.
	* @param party Party ArrayList stores all the parties that have joined the election.
	* @param number_candidate the total number of candidates added to the election.
	* @param num_seats the total number of seats won.
	* @param mywriter write into the audit file.
	*/
	public PO_sys(ArrayList<Candidate> candidate, ArrayList<Party> party,
				  int number_candidate, int num_seats, PrintWriter mywriter){
		this.candidates = candidate;
		this.parties = party;
		this.num_candidate = number_candidate;
		this.num_seats = num_seats;
		this.total_ballot = 0;
		this.mywriter = mywriter;

	}
	
	/**
	* Reads ballots from the CSV file.
	* @param num_ballot number of ballots in this file.
	* @param scanner used to read file.
	* @return void.
	* @exception no exception.
	*/
	public void readballot(int num_ballot, Scanner scanner){
      int index = total_ballot + 1;
      while(scanner.hasNextLine()){
          String line = scanner.nextLine();
          mywriter.printf("No.%d ballot is %s.%n",index,line);
          line = line.replaceAll("\\s",""); //remove whitespace
          for(int i = 0; i < line.length(); i++){
              if(line.charAt(i) == '1'){
                  Candidate candidate = candidates.get(i);
                  mywriter.printf("No.%d ballot assign to %s from party %s %n",index,candidate.getName(),candidate.getParty());
                  int vote = candidate.getVote();
                  candidates.get(i).setVote(vote+1);
                  mywriter.printf("%s has %d vote(s) now.%n",candidate.getName(),candidate.getVote());
                  break;
              }
          }
          index++;
      }
		total_ballot = total_ballot + num_ballot;
		mywriter.flush();
	}

}
