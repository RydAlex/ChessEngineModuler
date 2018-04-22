package chess.database.dao;

import chess.database.dao.core.CoreDAO;
import chess.database.entities.EngineName;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aleksanderr on 21/06/17.
 */
@NoArgsConstructor
@Repository
public class EngineNameDAO extends CoreDAO<EngineName> {


    public List<EngineName> findByName(String name) {
        startConnection();
        List engineNames = em.createQuery(
                "FROM EngineName engine WHERE engine.engineName LIKE :name")
                .setParameter("name", name)
                .getResultList();
        closeConnection();
        return engineNames;
    }

    public List<EngineName> findByNameAndType(String name, String typeOfGame) {
        startConnection();
        List engineNames = em.createQuery(
                "FROM EngineName engine WHERE engine.engineName LIKE :name AND engine.typeOfGame LIKE :gameType")
                .setParameter("name", name)
                .setParameter("gameType", typeOfGame)
                .getResultList();
        closeConnection();
        return engineNames;
    }


    public List<EngineName> findAll(){
        startConnection();
        List listOfResults = em.createQuery("from EngineName").getResultList();
        closeConnection();
        return listOfResults;
    }
}
