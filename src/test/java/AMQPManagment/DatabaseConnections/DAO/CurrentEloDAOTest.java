package AMQPManagment.DatabaseConnections.DAO;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.dao.CurrentEloDAO;
import chess.database.entities.CurrentElo;
import org.junit.Test;

/**
 * Created by aleksanderr on 08/07/17.
 */
public class CurrentEloDAOTest {
    @Test
    public void canSearchEloDependsOnEngineName(){
        CurrentEloDAO currentEloDAO = new CurrentEloDAO();
        CurrentElo currElo = currentEloDAO.findByEngineName("daydreamer", TypeOfMessageExtraction.ELO_SIMPLE).get(0);
    }
}