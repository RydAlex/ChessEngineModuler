package chess.utils.name.spy;

import chess.engine.processor.core.enginemechanism.OsCheck;
import chess.engine.processor.interfaces.EngineRunnerImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by aleksanderr on 30/05/17.
 */
@Slf4j
public class EngineSearcher {

    public static List<List<String>> createClusterOfChessEngines() {
        int engineClusterSize = 10; //40
        int amountOfClusters = 2; //20

        List<List<String>> listOfEnginesToReturn = new LinkedList<>();
        List<String> engineList = new EngineRunnerImpl().getEngineNames();


        List<String> listOfEnginesInCluster = new LinkedList<>();
        for(int j=0; j<engineClusterSize; j++){
            listOfEnginesInCluster.add("stockfish9");
        }
        listOfEnginesToReturn.add(listOfEnginesInCluster);
        for(int i=listOfEnginesToReturn.size() ; listOfEnginesToReturn.size() < amountOfClusters; i++){
            listOfEnginesInCluster = new LinkedList<>();
            for(int j=0; j<engineClusterSize; j++){
                listOfEnginesInCluster.add(engineList.get(new Random().nextInt(engineList.size())));
            }
            listOfEnginesToReturn.add(listOfEnginesInCluster);
        }
        return listOfEnginesToReturn;
    }

    public static String getRandomChessEngineName(){
        List<String> engineList = new EngineRunnerImpl().getEngineNames();
        return engineList.get(new Random().nextInt(engineList.size()));
    }
}
