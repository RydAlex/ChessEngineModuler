package AMQPManagment.DatabaseConnections.DAO;

import AMQPManagment.DatabaseConnections.DAO.DAOCore.CoreDAO;
import AMQPManagment.DatabaseConnections.Entities.EloGamesHistory;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by aleksanderr on 21/06/17.
 */
@NoArgsConstructor
@Repository
public class EloGamesHistoryDAO extends CoreDAO<EloGamesHistory>{


    public EloGamesHistory find(int gameType){
        return em.find(EloGamesHistory.class, 1);
    }

    public List findByName(String name) {
        return em.createQuery(
                "FROM EloGamesHistory curr WHERE curr.rule LIKE :rule")
                .setParameter("rule", name)
                .getResultList();
    }


    public List<EloGamesHistory> findAll(){
        return em.createQuery("from EloGamesHistory").getResultList();
    }

}
