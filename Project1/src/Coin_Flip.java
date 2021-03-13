import java.math.*;
import java.util.Random;

/**
 * Used when more than two candidates are tied in the election.
 * Using a random function in Java, select a candidate at random
 * and return the number of the selected candidate.
 * @author Zilong He
 * @version 1.0
 */

public class Coin_Flip {
    /**
     * Returns the number of the randomly selected candidate among several tied candidates.
     * @param N_candi number of tied candidates.
     * @return the index number of randomly selected candidate.
     * @exception no exception.
     */
    public static int flip(int N_candi){
        int N_selected;
        Random rand = new Random();
        int rand_int1 = rand.nextInt(N_candi);
        N_selected = rand_int1;
        return N_selected;
    }

//    public static void main(String[] args) {
//        // write your code here
//        //System.out.println("Hello");
//        int Nselect = flip(3);
//        System.out.println(Nselect);
//    }
}
