package chess.database.dao;

import chess.database.dao.core.CoreDAO;
import chess.database.entities.Cluster;
import chess.redis.RedisGeneticAlgorithmManager;

import javax.persistence.Query;
import java.util.List;

public class ClusterDAO extends CoreDAO<Cluster> {

    public List<Cluster> findTopClustersInCurrentEpoch(Integer amountOfTopClusters) {
        startConnection();
        Query q = em.createNamedQuery("getTopClusters");
        q.setParameter("epochNumber", RedisGeneticAlgorithmManager.getEpochCounter());
        q.setParameter("bestLimit", amountOfTopClusters);
        List<Cluster> clusters = q.getResultList();
        closeConnection();
        return clusters;
    }

    public List<Cluster> findAllClustersInCurrentEpoch() {
        startConnection();
        Query q = em.createNamedQuery("getAllClusters");
        q.setParameter("epochNumber", RedisGeneticAlgorithmManager.getEpochCounter());
        List<Cluster> clusters = q.getResultList();
        closeConnection();
        return clusters;
    }

}
