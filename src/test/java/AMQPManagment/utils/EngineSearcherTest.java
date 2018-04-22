package AMQPManagment.utils;

import chess.utils.name.spy.EngineSearcher;
import chess.utils.parsing.objects.ClusterBattlePairingService;
import chess.utils.parsing.objects.EnginesCluster;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Created by aleksanderr on 08/07/17.
 */
public class EngineSearcherTest {
    @Test
    public void engineSearcherReturnProperNumbersOfEngines(){
        List<List<String>> pairsOfGames = EngineSearcher.createPairsOfGames();
        Assert.assertEquals(8, pairsOfGames.size());//Mac value
    }

    @Test
    public void createProperClusterBattles(){
        Set<List<EnginesCluster>> enginesClusters = ClusterBattlePairingService.createPairs();
        Assert.assertEquals(3,enginesClusters.size());
    }
}
