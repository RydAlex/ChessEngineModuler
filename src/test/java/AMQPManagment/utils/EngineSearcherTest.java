package AMQPManagment.utils;

import chess.utils.name.spy.EngineSearcher;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by aleksanderr on 08/07/17.
 */
public class EngineSearcherTest {
    @Test
    public void engineSearcherReturnProperNumbersOfEngines(){
        List<List<String>> pairsOfGames = EngineSearcher.createPairsOfGames();
        Assert.assertEquals(8, pairsOfGames.size());//Mac value
    }
}