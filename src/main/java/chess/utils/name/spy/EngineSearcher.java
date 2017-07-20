package chess.utils.name.spy;

import chess.engine.processor.interfaces.EngineRunnerImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by aleksanderr on 30/05/17.
 */
public class EngineSearcher {
    public static List<String> searchFewRandomEngineNames(int numberOfEngines){
        LinkedList<String> listOfNames = new LinkedList<>();
        List<String> engineList = new EngineRunnerImpl().getEngineNames();
        for(int i=0 ; i<numberOfEngines ; i++){
            int randomEngineNumber = new Random().nextInt(engineList.size());
            listOfNames.add(engineList.get(randomEngineNumber));
            engineList.remove(randomEngineNumber);
        }
        return listOfNames;
    }

    public static List<List<String>> createPairsOfGames() {
        List<List<String>> listOfEnginesPairsToReturn = new LinkedList<>();
        List<String> engineList = new EngineRunnerImpl().getEngineNames();
        String valuesAlreadyTaken = " ";
        Random random = new Random();
        for(int i=0 ; i<engineList.size()/2 ; i++){
            LinkedList<String> engineNames = new LinkedList<>();
            int engineOneListNumber = -1;
            int engineTwoListNumber = -1;
            boolean engineFreeFound = false;
            while(!engineFreeFound) {
                engineOneListNumber = random.nextInt(engineList.size());
                if(!valuesAlreadyTaken.contains(" " + engineOneListNumber + " ")){
                    engineFreeFound = true;
                }
            }
            valuesAlreadyTaken += engineOneListNumber+" ";
            engineFreeFound = false;
            while(!engineFreeFound) {
                engineTwoListNumber = random.nextInt(engineList.size());
                if (!valuesAlreadyTaken.contains(" " + engineTwoListNumber + " ")) {
                    engineFreeFound = true;
                }
            }
            valuesAlreadyTaken += engineTwoListNumber + " ";
            engineNames.add(engineList.get(engineOneListNumber));
            engineNames.add(engineList.get(engineTwoListNumber));
            listOfEnginesPairsToReturn.add(engineNames);
        }
        return listOfEnginesPairsToReturn;
    }
}
