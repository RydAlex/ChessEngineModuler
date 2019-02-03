package chess.utils;

import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class ChessClusterTest {

    @Test
    public void is_sameClusterEngine_comparedTogetherAre_false(){

        ChessCluster clusterOne = new ChessCluster();
        clusterOne.setCluster(new Cluster(1,1, 4, 5, 1500));
        LinkedList<Engine> engines = new LinkedList<>();
        engines.add(new Engine(1, "stockfish9"));
        engines.add(new Engine(1, "stockfish9"));
        clusterOne.setEngineList(engines);

        ChessCluster clusterTwo = new ChessCluster();
        clusterTwo.setCluster(new Cluster(2,1, 4, 5, 1700));
        engines = new LinkedList<>();
        engines.add(new Engine(1, "stockfish9"));
        engines.add(new Engine(1, "stockfish9"));
        clusterTwo.setEngineList(engines);

        Assert.assertTrue(clusterOne.equals(clusterTwo));
    }

    @Test
    public void is_differentClusterEngine_comparedTogether_false(){

        ChessCluster clusterOne = new ChessCluster();
        clusterOne.setCluster(new Cluster(1,1, 4, 5, 1500));
        LinkedList<Engine> engines = new LinkedList<>();
        engines.add(new Engine(1, "stockfish9"));
        engines.add(new Engine(1, "stockfish9"));
        clusterOne.setEngineList(engines);

        ChessCluster clusterTwo = new ChessCluster();
        clusterTwo.setCluster(new Cluster(2,1, 4, 5, 1700));
        engines = new LinkedList<>();
        engines.add(new Engine(1, "stockfish9"));
        engines.add(new Engine(1, "komodo8"));
        clusterTwo.setEngineList(engines);

        Assert.assertFalse(clusterOne.equals(clusterTwo));
    }

}