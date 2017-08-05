package chess.algorithms.elo;

import org.junit.Test;

/**
 * Created by aleksanderr on 25/07/17.
 */
public class EloValueBinaryFinderTest {

    @Test
    public void eloValueFinderTest(){
        System.out.println(EloAlgorithm.calculateRating(1441,1041,EloGameResultValue.WIN));

        System.out.println(EloAlgorithm.fetchOponentRatingFromDifference(1441,1444,EloGameResultValue.WIN));
    }
}