package chess.database.dao;

import chess.database.entities.Cluster;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class EngineDAOTest {

    @Test
    public void fetchEnginesForOneCluster(){
        EngineDAO engineDAO = new EngineDAO();
        Cluster cluster = new Cluster();
        cluster.setId(1);
        cluster.setWhiteGames(0);
        cluster.setBlackGames(0);
        cluster.setEloScore(1000);
        cluster.setEpochNumber(1);
        List engines = engineDAO.findListOfEnginesForGivenCluster(cluster);
        Assert.assertEquals(engines.size(), 5);
    }
}