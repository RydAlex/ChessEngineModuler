package chess.database.dao;

import chess.database.entities.Cluster;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ClusterDAOTest {

    @Test
    public void testAbilityToReturnTopClusters(){
        ClusterDAO clusterDAO = new ClusterDAO();
        List<Cluster> cluster = clusterDAO.findTopClustersInCurrentEpoch(4);
        Assert.assertEquals(cluster.size(), 4);
    }
}