package AMQPManagment.DatabaseConnections.DAO;

import AMQPManagment.DatabaseConnections.DAO.DAOCore.CoreDAO;
import AMQPManagment.DatabaseConnections.Entities.EngineName;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
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
                "FROM EngineName engine WHERE engine.engineName LIKE :name AND engine.type_of_game_used_by_that_engine LIKE :gameType")
                .setParameter("name", name)
                .setParameter("gameType", typeOfGame)
                .getResultList();
        return engineNames;
    }


    public List<EngineName> findAll(){
        return em.createQuery("from EngineName").getResultList();
    }
}
