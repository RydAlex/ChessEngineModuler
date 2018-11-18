package chess.utils.name.spy;

import chess.engine.processor.interfaces.EngineRunnerImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by aleksanderr on 30/05/17.
 */
@Slf4j
public class EngineSearcher {

    public static List<List<String>> createClusterOfChessEngines() {
        int engineClusterSize = 30;
        //int amountOfClusters = 20;

        List<List<String>> listOfEnginesToReturn = new LinkedList<>();
        List<String> engineList = new EngineRunnerImpl().getEngineNames();


        List<String> listOfEnginesInCluster = new LinkedList<>();
        for(int j=0; j<engineClusterSize; j++){
            listOfEnginesInCluster.add("stockfish9");
        }
        listOfEnginesToReturn.add(listOfEnginesInCluster);
        listOfEnginesInCluster = new LinkedList<>();
        for(int j=0; j<engineClusterSize; j++){
            listOfEnginesInCluster.add("stockfish9");
        }
        listOfEnginesInCluster.set(10,"cheng439");
        listOfEnginesInCluster.set(11,"stockfish7");
        listOfEnginesInCluster.set(12,"komodo9");
        listOfEnginesInCluster.set(15,"komodo8");
        listOfEnginesInCluster.set(16,"cheng439");
        listOfEnginesInCluster.set(17,"stockfish8");
        listOfEnginesInCluster.set(18,"andscacs93");
        listOfEnginesInCluster.set(21,"stockfish8");
        listOfEnginesInCluster.set(25,"stockfish8");
        listOfEnginesInCluster.set(29,"komodo7");
        listOfEnginesToReturn.add(listOfEnginesInCluster);

        return listOfEnginesToReturn;
    }

    public static String getRandomChessEngineName(){
        List<String> engineList = new EngineRunnerImpl().getEngineNames();
        return engineList.get(new Random().nextInt(engineList.size()));
    }
}
