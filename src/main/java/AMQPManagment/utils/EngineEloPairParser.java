package AMQPManagment.utils;

import AMQPManagment.DatabaseConnections.DAO.CurrentEloDAO;
import AMQPManagment.utils.data.EngineEloPair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksanderr on 09/07/17.
 */
public class EngineEloPairParser {
    public static List<EngineEloPair> findElosForEngineNamesAndCreateEngineEloPair(List<String> listOfEngines) {
        List<EngineEloPair> eloEngine = new LinkedList<>();
        CurrentEloDAO elo = new CurrentEloDAO();
        for(String engineName : listOfEngines){
            EngineEloPair engineEloPair = new EngineEloPair();
            engineEloPair.setEngineName(engineName);
            engineEloPair.setEloValue(1000);//TODO: GET PROPER VALUES
            eloEngine.add(engineEloPair);
        }
        return eloEngine;
    }
}
