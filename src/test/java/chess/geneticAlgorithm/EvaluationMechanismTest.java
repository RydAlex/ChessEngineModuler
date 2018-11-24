package chess.geneticAlgorithm;

import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.utils.ChessCluster;
import chess.utils.ChessClusterBattle;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class EvaluationMechanismTest {

    @Test
    public void evaluate() {
        LinkedList<ChessCluster> chessClusters = new LinkedList<>();

        LinkedList<Engine> engines = new LinkedList<>();
        engines.add(new Engine(1, "tadata"));
        engines.add(new Engine(2, "tada"));
        engines.add(new Engine(3, "hehe"));
        engines.add(new Engine(4, "hihi"));
        engines.add(new Engine(5, "pampam"));

        ChessCluster searchCluster = new ChessCluster(new Cluster(1,1300),engines);

        chessClusters.add(searchCluster);
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        chessClusters.add(new ChessCluster(new Cluster(1,1300),engines));
        List<ChessClusterBattle> clusters = EvaluationMechanism.createChessClusterBattles(chessClusters);
        int i=0;
        for(ChessClusterBattle battle: clusters){
            if(battle.getChessClusterOne().equals(battle.getChessClusterTwo())){
                Assert.fail("There are two same clusters in battle");
            }
            if(battle.getChessClusterOne().equals(searchCluster)){
                i++;
            } else if(battle.getChessClusterTwo().equals(searchCluster)){
                i++;
            }
        }
        Assert.assertEquals(i, 76);
        Assert.assertEquals(clusters.size(), 760);
    }
}