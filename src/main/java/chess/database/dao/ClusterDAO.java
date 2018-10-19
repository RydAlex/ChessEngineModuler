package chess.database.dao;

import chess.database.dao.core.CoreDAO;
import chess.database.entities.Cluster;
import chess.redis.RedisGeneticAlgorithmManager;
import chess.redis.RedisManager;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
