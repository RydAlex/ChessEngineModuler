package AMQPManagment.DatabaseConnections.DAO;

import AMQPManagment.DatabaseConnections.DAO.DAOCore.CoreDAO;
import AMQPManagment.DatabaseConnections.Entities.Score;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by aleksanderr on 21/06/17.
 */
@NoArgsConstructor
@Repository
public class ScoreDAO extends CoreDAO<Score>{

    public Score find(int gameType){
        return em.find(Score.class, 1);
    }

    public List findByName(String name) {
        return em.createQuery(
                "FROM Score score WHERE score.rule LIKE :rule")
                .setParameter("rule", name)
                .getResultList();
    }


    public List<Score> findAll(){
        return em.createQuery("from Score").getResultList();
    }

}
