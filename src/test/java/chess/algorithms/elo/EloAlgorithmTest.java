package chess.algorithms.elo;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aleksanderr on 24/07/17.
 */
public class EloAlgorithmTest {
    @Test
    public void testElo(){
        Integer value = EloAlgorithm.calculateRating(400,800,EloGameResultValue.LOSE);
        Assert.assertEquals((Integer)397,value);
    }
}