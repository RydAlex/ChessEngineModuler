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


    public EngineName find(int gameType){
        return em.find(EngineName.class, 1);
    }

    public List<EngineName> findByName(String name) {
        List<EngineName> engineNames = em.createQuery(
                "FROM EngineName engine WHERE engine.engineName LIKE :name")
                .setParameter("name", name)
                .getResultList();
        return engineNames;
    }

    public List<EngineName> findByNameAndType(String name, String typeOfGame) {
        List<EngineName> engineNames = em.createQuery(
                "FROM EngineName engine WHERE engine.engineName LIKE :name AND engine.typeOfGameUsedByThatEngine LIKE :gameType")
                .setParameter("name", name)
                .setParameter("gameType", typeOfGame)
                .getResultList();
        return engineNames;
    }


    public List<EngineName> findAll(){
        return em.createQuery("from EngineName").getResultList();
    }
}
