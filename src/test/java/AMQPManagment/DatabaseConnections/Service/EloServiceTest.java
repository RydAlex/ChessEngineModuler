package AMQPManagment.DatabaseConnections.Service;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.service.EloService;
import org.junit.Test;

/**
 * Created by aleksanderr on 08/07/17.
 */
public class EloServiceTest {

    @Test
    public void CheckIfEloCanBeFetchedFromDatabase(){
        Integer integerVal = EloService.getEloValuesForEngineWithType("daydreamer", TypeOfMessageExtraction.ELO_SIMPLE);
    }

    @Test
    public void CheckIfEloCanBeUpdatedInDatabase(){
        EloService.updateEloValueForEntity("gull",TypeOfMessageExtraction.ELO_SIMPLE,1000,1030,true);
    }
}