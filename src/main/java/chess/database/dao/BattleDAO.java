package chess.database.dao;

import chess.database.dao.core.CoreDAO;
import chess.database.entities.Battle;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BattleDAO extends CoreDAO<Battle>{

    public List<Battle> fetchIfOneOfEnginesHaveNameAs(String engineName){
        startConnection();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Battle> criteria = criteriaBuilder.createQuery(Battle.class);
        Root<Battle> battleRoot = criteria.from(Battle.class);
        CriteriaQuery<Battle> battleOneIdJoinQuerry = criteria.where(criteriaBuilder.equal(battleRoot.join("engineNameOneId").get("engineName"), engineName));
        TypedQuery<Battle> battleOne = em.createQuery(battleOneIdJoinQuerry);
        List<Battle> battleOneHistories = battleOne.getResultList();

        criteria = criteriaBuilder.createQuery(Battle.class);
        battleRoot = criteria.from(Battle.class);
        CriteriaQuery<Battle> battleTwoIdJoinQuerry = criteria.where(criteriaBuilder.equal(battleRoot.join("engineNameTwoId").get("engineName"), engineName));
        TypedQuery<Battle> battleTwo = em.createQuery(battleTwoIdJoinQuerry);
        List<Battle> battleTwoHistories = battleTwo.getResultList();

        battleOneHistories.addAll(battleTwoHistories);
        closeConnection();
        return battleOneHistories;
    }


    public List<Battle> fetchOnlyWinEngines() {
        return getGamesWithBattleResult(true);
    }

    public List<Battle> fetchOnlyLoseEngines() {
        return getGamesWithBattleResult(false);
    }

    public List<Battle> fetchOnlyDrawEngines() {
        startConnection();
        List engineNames = em.createQuery(
                "FROM Battle battle WHERE battle.winOfFirst=NULL")
                .getResultList();
        closeConnection();
        return engineNames;
    }


    private List<Battle> getGamesWithBattleResult(Boolean winOrFalse) {
        startConnection();
        List engineNames = em.createQuery(
                "FROM Battle battle WHERE battle.winOfFirst= :winOrFalse")
                .setParameter("winOrFalse", winOrFalse)
                .getResultList();
        closeConnection();
        return engineNames;
    }

}
