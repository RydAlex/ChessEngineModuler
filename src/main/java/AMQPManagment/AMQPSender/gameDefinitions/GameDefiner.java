package AMQPManagment.AMQPSender.gameDefinitions;

import AMQPManagment.utils.data.EngineEloPair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksanderr on 09/07/17.
 */
class GameDefiner {
    List<String> getEngineNamesFromEngineEloPair(List<EngineEloPair> engineEloPairList){
        LinkedList<String> enginesNames = new LinkedList<>();
        for(EngineEloPair engineEloPair : engineEloPairList){
            enginesNames.add(engineEloPair.getEngineName());
        }
        return enginesNames;
    }
}
