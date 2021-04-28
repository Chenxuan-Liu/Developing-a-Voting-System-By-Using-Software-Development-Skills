import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.lang.*;
import java.util.*;
import javax.sound.midi.*;


public class PO_sys{

	private ArrayList<Candidate> candidates;
	private ArrayList<Party> parties;
	private int num_candidate, num_seats, total_ballot;
	private Coin_Flip coin = new Coin_Flip();
	private PrintWriter mywriter;

	public PO_sys(ArrayList<Candidate> candidate, ArrayList<Party> party,
				  int number_candidate, int num_seats, PrintWriter mywriter){
		this.candidates = candidate;
		this.parties = party;
		this.num_candidate = number_candidate;
		this.num_seats = num_seats;
		this.total_ballot = 0;
		this.mywriter = mywriter;

	}

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
