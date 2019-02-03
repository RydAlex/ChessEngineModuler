package chess.utils.name.spy;

import chess.engine.processor.interfaces.EngineRunnerImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Created by aleksanderr on 30/05/17.
 */
@Slf4j
public class EngineSearcher {

    public static List<List<String>> createClusterOfChessEngines() {
        int engineClusterSize = 30;
        int amountOfClusters = 20;

        List<List<String>> listOfEnginesToReturn = new LinkedList<>();
        List<String> engineList = new EngineRunnerImpl().getEngineNames();

        List<String> listOfEnginesInCluster;
        for (String engineName : engineList) {
            listOfEnginesInCluster = new LinkedList<>();
            for(int j=0; j<engineClusterSize; j++){
                listOfEnginesInCluster.add(engineName);
            }
            listOfEnginesToReturn.add(listOfEnginesInCluster);
        }
//        for(int i=listOfEnginesToReturn.size() ; listOfEnginesToReturn.size() < amountOfClusters; i++){
//            listOfEnginesInCluster = new LinkedList<>();
//            for(int j=0; j<engineClusterSize; j++){
//                listOfEnginesInCluster.add(engineList.get(new Random().nextInt(engineList.size())));
//            }
//            listOfEnginesToReturn.add(listOfEnginesInCluster);
//        }
        return listOfEnginesToReturn;
    }

    public static String getRandomChessEngineName(){
        List<String> engineList = new EngineRunnerImpl().getEngineNames();
        return engineList.get(new Random().nextInt(engineList.size()));
    }
}
