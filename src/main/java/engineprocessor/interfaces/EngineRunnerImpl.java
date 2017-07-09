package engineprocessor.interfaces;

import engineprocessor.core.enginemechanism.CommandQuery;
import engineprocessor.core.enginemechanism.EngineAvailabilityScanner;
import engineprocessor.core.enginemechanism.EngineProcessor;
import engineprocessor.core.utils.enums.CommandEnum;
import engineprocessor.core.utils.enums.GoEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by aleksanderr on 19/03/17.
 */
@Slf4j
public class EngineRunnerImpl implements EngineRunner {

    @Override
    public List<String> getEngineNames() {
        return EngineAvailabilityScanner.getInstance().returnListOfNames();
    }

    @Override
    public String RunEngineWithCommand(String engineName, CommandEnum command, String... attributes) {
        try{
            String enginePath = EngineAvailabilityScanner.getInstance().returnMapOfEnginePaths().get(engineName);
            EngineProcessor er = new EngineProcessor();
            CommandQuery commandQuery = er.setEngineConnection(enginePath);
            switch(command) {
                case QUIT:
                    commandQuery.exitTheGame();
                    break;
                case GO:
                    if(attributes.length == 1){
                        commandQuery.go(GoEnum.valueOf(attributes[0]));
                    } else {
                        commandQuery.go(GoEnum.valueOf(attributes[0]), Integer.getInteger(attributes[1]) );
                    }
                    return commandQuery.returnMoveWhichEngineFound();
                case NEW_GAME:
                    commandQuery.startNewGame();
                    break;
                case SET_POSITION:
                    commandQuery.setPosition(attributes[0]);
                    break;
                case IS_ENGINE_READY:
                    commandQuery.isEngineReady();
                    break;
                case GET_CHESS_INFORMATION:
                    commandQuery.getChessEngineInformation();
                    break;
                case DEBUG:
                    commandQuery.showActualLookOfGame();
                    break;

            }
            String answer="";
            for(String line: commandQuery.returnDataFromEngineResponse(true)){
                answer += line + " \n";
            }
            return answer;
        } catch (Exception e){
            throw new RuntimeException("Something strange happend here");
        }
    }

    @Override
    public String RunEngineWithGoTimeoutCommand(String engineName, String fenPosition, int timeout) {
        try {
            String enginePath = EngineAvailabilityScanner.getInstance().returnMapOfEnginePaths().get(engineName);
            EngineProcessor er = new EngineProcessor();
            CommandQuery commandQuery = er.setEngineConnection(enginePath);
            commandQuery
                    .getChessEngineInformation()
                    .startNewGame()
                    .setPosition(fenPosition)
                    .go(GoEnum.searchInTime, timeout);
            while (commandQuery.isListOfCommandHaveElements()) {
                sleep(100);
            }
            String msgFound = commandQuery.returnMoveWhichEngineFound();
            commandQuery.exitTheGame();
            er = null;
            commandQuery = null;
            System.gc();
            //log.info("Garbage collector broke connection with engines");
            return msgFound;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String RunEngineWithGoDepthCommand(String engineName, String fenPosition, int depth) {try {
        String enginePath = EngineAvailabilityScanner.getInstance().returnMapOfEnginePaths().get(engineName);
        EngineProcessor er = new EngineProcessor();
        CommandQuery commandQuery = er.setEngineConnection(enginePath);
        commandQuery
                .getChessEngineInformation()
                .startNewGame()
                .setPosition(fenPosition)
                .go(GoEnum.searchDepth, depth);
        while (commandQuery.isListOfCommandHaveElements()) {
            sleep(100);
        }
        String msgFound = commandQuery.returnMoveWhichEngineFound();
        commandQuery.exitTheGame();
        er = null;
        commandQuery = null;
        System.gc();
        //log.info("Garbage collector broke connection with engines");
        return msgFound;
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
        return "";
    }
}
