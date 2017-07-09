package AMQPManagment.utils;

import AMQPManagment.DatabaseConnections.Service.EloService;
import AMQPManagment.utils.data.EngineEloPair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksanderr on 09/07/17.
 */
public class EngineEloPairParser {
    public static List<EngineEloPair> findElosForEngineNamesAndCreateEngineEloPair(List<String> listOfEngines) {
        List<EngineEloPair> eloEngine = new LinkedList<>();
        for(String engineName : listOfEngines){
            EngineEloPair engineEloPair = new EngineEloPair();
            engineEloPair.setEngineName(engineName);
            engineEloPair.setEloValue(EloService.getEloValuesForEngine(engineName));
            eloEngine.add(engineEloPair);
        }
        return eloEngine;
    }
}
