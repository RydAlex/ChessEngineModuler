package chess.database.dao;

import chess.database.dao.core.CoreDAO;
import chess.database.entities.EloGamesHistory;
import chess.database.entities.EngineName;
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
public class EloGamesHistoryDAO extends CoreDAO<EloGamesHistory>{

    public List<EloGamesHistory> findByEngineName(String engineName) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<EloGamesHistory> criteria= criteriaBuilder.createQuery(EloGamesHistory.class);
        Root<EloGamesHistory> eloHistory = criteria.from(EloGamesHistory.class);
        CriteriaQuery<EloGamesHistory> eloJoinQuerry = criteria.where(criteriaBuilder.equal(eloHistory.join("engineNameId").get("engineName"), engineName));

        TypedQuery<EloGamesHistory> q = em.createQuery(eloJoinQuerry);
        List<EloGamesHistory> eloHistories = q.getResultList();
        return eloHistories;
    }
}
