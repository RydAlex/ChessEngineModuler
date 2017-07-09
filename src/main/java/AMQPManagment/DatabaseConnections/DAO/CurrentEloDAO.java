package AMQPManagment.DatabaseConnections.DAO;

import AMQPManagment.DatabaseConnections.DAO.DAOCore.CoreDAO;
import AMQPManagment.DatabaseConnections.Entities.CurrentElo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by aleksanderr on 21/06/17.
 */
@NoArgsConstructor
public class CurrentEloDAO extends CoreDAO<CurrentElo>{


    public CurrentElo find(int gameType){
        return em.find(CurrentElo.class, 1);
    }

    public List<CurrentElo> findByEngineName(String engineName) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<CurrentElo> criteria= criteriaBuilder.createQuery(CurrentElo.class);
        Root<CurrentElo> currentElo = criteria.from(CurrentElo.class);
        CriteriaQuery<CurrentElo> eloJoinQuerry = criteria.where(criteriaBuilder.equal(currentElo.join("engineNameId").get("engineName"), engineName));

        TypedQuery<CurrentElo> q = em.createQuery(eloJoinQuerry);
        List<CurrentElo> currentElos = q.getResultList();
        return currentElos;
    }


    public List<CurrentElo> findAll(){
        return em.createQuery("from CurrentElo").getResultList();
    }

}
