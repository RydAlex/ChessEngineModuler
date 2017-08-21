package chess.manager.data.parsers;

import chess.database.dao.CurrentEloDAO;
import chess.amqp.message.EngineEloPair;
import chess.utils.parsing.objects.EnginesCluster;

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

    public static List<EnginesCluster> findElosForEnginesInClusters(List<EnginesCluster> enginesClusters) {
        List<EnginesCluster> clusterToReturn = new LinkedList<>();
        CurrentEloDAO eloDAO = new CurrentEloDAO();
        for(EnginesCluster cluster: enginesClusters){
            for(EngineEloPair eloPair : cluster.getEngineList()){
                eloPair.setEloValue(
                        eloDAO.findByEngineNameAndType(
                                eloPair.getEngineName()+"_"+cluster.getPlayRule()
                        ).get(0).getEloValue()
                );
            }
            clusterToReturn.add(cluster);
        }
        return clusterToReturn;
    }
}
