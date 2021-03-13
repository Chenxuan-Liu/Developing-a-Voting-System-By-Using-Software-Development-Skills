import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Chenxuan Liu
 * @version 1.0
 */

public class Coin_FlipTest {
    /**
     * This class tests the correctness of the functions in the Coin_Flip class.
     * @return IOException.
     */
    @Test
    public void flip() {
        Coin_Flip coin = new Coin_Flip();

        for (int i = 1; i < 100; i++){
            int randNum = coin.flip(i);
            assertTrue(randNum < i);
            assertTrue(randNum > 0);
        }
    }
}