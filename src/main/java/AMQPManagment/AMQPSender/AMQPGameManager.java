package AMQPManagment.AMQPSender;

import AMQPManagment.utils.EngineSearcher;
import AMQPManagment.utils.MessageExtractor;
import AMQPManagment.utils.TypeOfMessageExtraction;
import engineprocessor.core.enginemechanism.FenGenerator;
import engineprocessor.interfaces.EngineRunner;
import engineprocessor.interfaces.EngineRunnerImpl;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Created by aleksanderr on 04/06/17.
 */
@Slf4j
public class AMQPGameManager {
    private List<String> firstGroup = new LinkedList<>();
    private List<String> secondGroup = new LinkedList<>();

    public static void main(String[] argv){
        AMQPGameManager amqp = new AMQPGameManager();
        amqp.createSimpleDepthGame(4,5, TypeOfMessageExtraction.RANDOM);
    }

    private void createSimpleDepthGame(int numberOfEngine, int depth, TypeOfMessageExtraction type){
        FenGenerator fenGenerator = new FenGenerator();
        String fenStringPositions = fenGenerator.returnFenStringPositions();
        //setEnginesInGroups(numberOfEngine);

        AMQPSender sender = new AMQPSender();
        MessageExtractor.getMoveFromEngineResults(
                type,
                sender.sendMessageWithDepthRule(
                        fenStringPositions,
                        depth,
                        type ,
                        true ,
                        1,
                        EngineSearcher.searchFewRandomEngineNames(numberOfEngine)
                )
        );
    }

    private boolean createSimpleTimeoutGame(int numberOfEngine, int timeout, TypeOfMessageExtraction type){
        String firstGroupResult = null;
        String secondGroupResult = null;

        FenGenerator fenGenerator = new FenGenerator();
        String fenStringPositions = fenGenerator.returnFenStringPositions();
        setEnginesInGroups(numberOfEngine);
        AMQPSender sender = new AMQPSender();

        while(true){
            //calculateFirstGroup
          //  firstGroupResult = MessageExtractor.getMoveFromEngineResults(type, sender.sendMessageWithTimeoutRule(fenStringPositions, timeout, type, firstGroup));
            if(firstGroupResult == null) {
                break;
            }
            fenGenerator.insertMove(firstGroupResult);
            fenStringPositions = fenGenerator.returnFenStringPositions();

            //calculateSecondGroup
          //  secondGroupResult = MessageExtractor.getMoveFromEngineResults(type, sender.sendMessageWithTimeoutRule(fenStringPositions, timeout, type, secondGroup));
            if(secondGroupResult == null) {
                break;
            }
            fenGenerator.insertMove(secondGroupResult);
            fenStringPositions = fenGenerator.returnFenStringPositions();
        }
        return whoWin(firstGroupResult, secondGroupResult);
    }

    private boolean whoWin(String firstGroupResult, String secondGroupResult) {
        if(firstGroupResult == null){
            log.info("second group WIN");
            return false;
        } else if(secondGroupResult == null) {
            log.info("first group WIN");
            return true;
        } else {
            throw new RuntimeException("Weird end of Game - noone win but its end");
        }
    }


    private void setEnginesInGroups(int numberOfEngine) {
        List<String> engineNames = EngineSearcher.searchFewRandomEngineNames(numberOfEngine);
        log.info("engineNames for a game: " + engineNames.toString());
        for(int engineNameNumber=0 ; engineNameNumber<numberOfEngine ; engineNameNumber++){
            if(engineNameNumber < numberOfEngine/2){
                firstGroup.add(engineNames.get(engineNameNumber));
            } else {
                secondGroup.add(engineNames.get(engineNameNumber));
            }
        }
    }
}
