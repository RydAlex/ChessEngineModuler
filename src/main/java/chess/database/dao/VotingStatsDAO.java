package chess.database.dao;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.dao.core.CoreDAO;
import chess.database.entities.EngineName;
import chess.database.entities.VotingStats;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class VotingStatsDAO extends CoreDAO<VotingStats> {


	public List<VotingStats> findByEngineName(String votePackName, String engineName, TypeOfMessageExtraction typeOfGame) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<VotingStats> criteria= criteriaBuilder.createQuery(VotingStats.class);
		Root<VotingStats> fromVotingStats = criteria.from(VotingStats.class);
		Join<VotingStats, EngineName> join = fromVotingStats.join("engineNameId");
		CriteriaQuery<VotingStats> eloJoinQuerry = criteria
				.where(
					criteriaBuilder.and(
							criteriaBuilder.equal(
									join.get("engineName"), engineName),
							criteriaBuilder.equal(
									join.get("typeOfGameUsedByThatEngine"), typeOfGame.getTypeOfGame()),
							criteriaBuilder.equal(
									fromVotingStats.get("votePackName"), votePackName)
					)
				);

		TypedQuery<VotingStats> q = em.createQuery(eloJoinQuerry);
		List<VotingStats> currentElos = q.getResultList();
		return currentElos;
	}


}