package chess.database.dao;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.dao.core.CoreDAO;
import chess.database.entities.CurrentElo;
import chess.database.entities.EngineName;
import lombok.NoArgsConstructor;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by aleksanderr on 21/06/17.
 */
@NoArgsConstructor
public class CurrentEloDAO extends CoreDAO<CurrentElo>{


    public List<CurrentElo> findByEngineNameAndType(String engineName, TypeOfMessageExtraction typeOfGame) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<CurrentElo> criteria= criteriaBuilder.createQuery(CurrentElo.class);
        Root<CurrentElo> currentElo = criteria.from(CurrentElo.class);
        Join<CurrentElo, EngineName> join = currentElo.join("engineNameId");
        CriteriaQuery<CurrentElo> eloJoinQuerry = criteria.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(
                                join.get("engineName"), engineName),
                        criteriaBuilder.equal(
                                join.get("typeOfGameUsedByThatEngine"), typeOfGame.getTypeOfGame())
                )
        );

        TypedQuery<CurrentElo> q = em.createQuery(eloJoinQuerry);
        List<CurrentElo> currentElos = q.getResultList();
        return currentElos;
    }

    public List<CurrentElo> findByEngineNameAndType(String engineName){
        return findByEngineNameAndType(engineName, TypeOfMessageExtraction.ELO_SIMPLE);
    }

}
