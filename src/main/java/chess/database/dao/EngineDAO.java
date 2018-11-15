package chess.database.dao;

import chess.database.dao.core.CoreDAO;
import chess.database.entities.Cluster;
import chess.database.entities.Engine;

import javax.persistence.Query;
import java.util.List;

public class EngineDAO extends CoreDAO<Engine> {


    public List<Engine> findListOfEnginesForGivenCluster(Cluster cluster) {
        startConnection();
        Query q = em.createNamedQuery("getEnginesWhichAreUsedInCluster");
        q.setParameter("clusterNumber", cluster.getId());
        List<Engine> engines = q.getResultList();
        closeConnection();
        return engines;
    }

    public Engine getEngineByName(String engineName) {
        startConnection();
        Query q = em.createNamedQuery("getEngineByName");
        q.setParameter("engineName", engineName);
        List<Engine> engines = q.getResultList();
        closeConnection();
        if(engines.size() > 0){
            return engines.get(0);
        }
        return null;
    }
}
