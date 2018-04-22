package chess.database.dao;

import chess.database.dao.core.CoreDAO;
import chess.database.entities.EloEnginesHistory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EloEnginesHistoryDAO extends CoreDAO<EloEnginesHistory> {

    public List<EloEnginesHistory> findByEngineName(String engineName) {
        startConnection();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<EloEnginesHistory> criteria= criteriaBuilder.createQuery(EloEnginesHistory.class);
        Root<EloEnginesHistory> eloHistory = criteria.from(EloEnginesHistory.class);
        CriteriaQuery<EloEnginesHistory> eloJoinQuerry = criteria.where(criteriaBuilder.equal(eloHistory.join("engineNameId").get("engineName"), engineName));

        TypedQuery<EloEnginesHistory> q = em.createQuery(eloJoinQuerry);
        List<EloEnginesHistory> eloHistories = q.getResultList();
        closeConnection();
        return eloHistories;
    }
}
