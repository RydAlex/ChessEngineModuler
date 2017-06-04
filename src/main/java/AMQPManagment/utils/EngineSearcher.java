package AMQPManagment.utils;

import engineprocessor.interfaces.EngineRunnerImpl;

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

}
