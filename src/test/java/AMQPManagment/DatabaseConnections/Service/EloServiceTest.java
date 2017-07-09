package AMQPManagment.DatabaseConnections.Service;

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
        List<String> enginesList = new LinkedList<>();
        enginesList.add("daydreamer");
        enginesList.add("gull");
        List<Integer> integerVal = EloService.getEloValuesForEngines(enginesList);
        Assert.assertEquals(integerVal.size(),2);
    }

    @Test
    public void CheckIfEloCanBeUpdatedInDatabase(){
        EloService.updateEloValueForEntity("gull",1000,1030,true);
    }
}