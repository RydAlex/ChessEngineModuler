package chess.database.dao;

import chess.database.dao.core.CoreDAO;
import chess.database.entities.RandomGameResult;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aleksanderr on 21/06/17.
 */
@NoArgsConstructor
@Repository
public class RandomGameResultDAO extends CoreDAO<RandomGameResult>{


    public RandomGameResult find(int gameType){
        return em.find(RandomGameResult.class, 1);
    }

    public List findByName(String name) {
        return em.createQuery(
                "FROM RandomGameResult curr WHERE curr.rule LIKE :rule")
                .setParameter("rule", name)
                .getResultList();
    }


    public List<RandomGameResult> findAll(){
        return em.createQuery("from RandomGameResult").getResultList();
    }

}
