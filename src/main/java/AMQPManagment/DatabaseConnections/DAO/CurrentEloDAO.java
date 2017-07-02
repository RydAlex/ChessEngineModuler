package AMQPManagment.DatabaseConnections.DAO;

import AMQPManagment.DatabaseConnections.DAO.DAOCore.CoreDAO;
import AMQPManagment.DatabaseConnections.Entities.CurrentElo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by aleksanderr on 21/06/17.
 */
@NoArgsConstructor
@Repository
public class CurrentEloDAO extends CoreDAO<CurrentElo>{


    public CurrentElo find(int gameType){
        return em.find(CurrentElo.class, 1);
    }

    public List<CurrentElo> findByEngineName(String engineName) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<CurrentElo> criteria= criteriaBuilder.createQuery(CurrentElo.class);
        Root<CurrentElo> currentElo = criteria.from(CurrentElo.class);
        CriteriaQuery<CurrentElo> eloJoinQuerry = criteria.where(criteriaBuilder.equal(currentElo.join("engine_name_id").get("engine_name"), engineName));

        TypedQuery<CurrentElo> q = em.createQuery(eloJoinQuerry);
        return q.getResultList();
    }


    public List<CurrentElo> findAll(){
        return em.createQuery("from CurrentElo").getResultList();
    }

}
