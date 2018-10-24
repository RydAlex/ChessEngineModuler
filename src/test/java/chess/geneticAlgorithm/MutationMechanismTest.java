package chess.geneticAlgorithm;

import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.utils.ChessCluster;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class MutationMechanismTest {

    @Test
    public void createNewClusterByMutation() {
        ChessCluster chessCluster = new ChessCluster();

        Cluster cluster = new Cluster();
        cluster.setId(1);
        cluster.setEloScore(1500);
        cluster.setEpochNumber(1);
        chessCluster.setCluster(cluster);

        LinkedList<Engine> chessEngines = new LinkedList<>();
        Engine engine = new Engine();
        engine.setEngineName("hehe");
        engine.setId(1);
        chessEngines.add(engine);

        Engine engine2 = new Engine();
        engine2.setEngineName("hehe2");
        engine2.setId(2);
        chessEngines.add(engine2);

        Engine engine3 = new Engine();
        engine3.setEngineName("hehe3");
        engine3.setId(3);
        chessEngines.add(engine3);

        Engine engine4 = new Engine();
        engine4.setEngineName("hehe4");
        engine4.setId(4);
        chessEngines.add(engine4);
        chessCluster.setEngineList(chessEngines);

        ChessCluster newCluster = MutationMechanism.createNewClusterByMutation(chessCluster);
        Assert.assertNotEquals(newCluster, chessCluster);
    }
}