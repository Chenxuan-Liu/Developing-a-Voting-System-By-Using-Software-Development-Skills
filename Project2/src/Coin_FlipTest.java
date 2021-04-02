import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the correctness of the functions in the Coin_Flip class.
 * @author Chenxuan Liu
 * @version 1.0
 */

public class Coin_FlipTest {
    @Test
    public void flip() {
        Coin_Flip coin = new Coin_Flip();

        for (int i = 1; i < 100; i++){
            int randNum = coin.flip(i);
            assertTrue(randNum < i);
            assertTrue(randNum >= 0);
        }
    }
}