package AMQPManagment.DatabaseConnections.Service;

import AMQPManagment.utils.TypeOfMessageExtraction;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

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