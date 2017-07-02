package AMQPManagment.DatabaseConnections.DAO;

import AMQPManagment.DatabaseConnections.DAO.DAOCore.CoreDAO;
import AMQPManagment.DatabaseConnections.Entities.DepthWith2MoreEloUseWinLose;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aleksanderr on 21/06/17.
 */
@NoArgsConstructor
@Repository
public class DepthWith2MoreEloUseWinLoseDAO extends CoreDAO<DepthWith2MoreEloUseWinLose>{

    public DepthWith2MoreEloUseWinLose find(int gameType){
        return em.find(DepthWith2MoreEloUseWinLose.class, gameType);
    }

    public List findByName(String name) {
        return em.createQuery(
                "FROM DepthWith2MoreEloUseWinLose d WHERE d.rule LIKE :rule")
                .setParameter("rule", name)
                .getResultList();
    }


    public List<DepthWith2MoreEloUseWinLose> findAll(){
        return em.createQuery("from DepthWith2MoreEloUseWinLose").getResultList();
    }
}
