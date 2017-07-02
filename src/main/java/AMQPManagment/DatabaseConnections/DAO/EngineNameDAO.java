package AMQPManagment.DatabaseConnections.DAO;

import AMQPManagment.DatabaseConnections.DAO.DAOCore.CoreDAO;
import AMQPManagment.DatabaseConnections.Entities.EngineName;
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

    public List findByName(String name) {
        return em.createQuery(
                "FROM EngineName curr WHERE curr.rule LIKE :rule")
                .setParameter("rule", name)
                .getResultList();
    }


    public List<EngineName> findAll(){
        return em.createQuery("from EngineName").getResultList();
    }
}
