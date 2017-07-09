package engineprocessor.core.enginemechanism;

import engineprocessor.core.utils.EngineResponse;
import engineprocessor.core.utils.enums.GoEnum;
import lombok.extern.slf4j.Slf4j;
import org.scalatest.Engine;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

/**
 * A list of command which can be send
 * Created by aleksanderr on 27/04/16.
 */
@Slf4j
public class CommandQuery {


    CommandQuery(EngineProcessor engineHandler){
        this.engineHandler = engineHandler;
    }

    private EngineProcessor engineHandler;
    String processedCommand = "Initialize";
    private BlockingQueue<String> commandList = new LinkedBlockingQueue<>();
    private BlockingQueue<EngineResponse> resultsOfCommand = new LinkedBlockingQueue<>();

    public boolean isListOfCommandHaveElements(){
        return commandList.size() != 0;
    }

    String getCommand() {
        try {
            processedCommand = commandList.take();
            if(processedCommand.contains("depth")){
                log.info("Depth command was send - " + processedCommand);
            } else if(processedCommand.contains("timeout")){
                log.info("Timout command was send");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return processedCommand;
    }

    public void clearMessagesInLogs() {
        resultsOfCommand.clear();
        commandList.clear();
    }

    private ArrayList<String> returnCommandsProcessedByEngine(){
        return returnDataFromEngineResponse(false);
    }

    private ArrayList<String> returnMessagesFromEngineResponse(){
        return returnDataFromEngineResponse(true);
    }


    void setResultOfCommand(String resultLine){
        EngineResponse er = new EngineResponse(processedCommand,resultLine);
        resultsOfCommand.add(er);
    }


    public String returnMoveWhichEngineFound() {
        return returnMoveWhichEngineFound(20000);
    }

    public String returnMoveWhichEngineFound(int timeout){
        timeout=timeout*3;
        String moveFound = "";
        ArrayList<String> answerList;
        try {
            retryWaitOnAnswer(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        answerList = returnMessagesFromEngineResponse();
        if(answerList != null){
            for(String answer : answerList){
                if(answer.contains("bestmove")){
                    String[] answerParts = answer.split(" ");
                    if(answerParts.length<2){
                        System.out.println(answer);
                        break;
                    }
                    moveFound = answerParts[1];
                    break;
                }
            }
        }
        return moveFound;
    }

    public CommandQuery getChessEngineInformation() {
        commandList.add("uci");
        return this;
    }

    public CommandQuery showActualLookOfGame() {
        commandList.add("d");
        return this;
    }

    public CommandQuery isEngineReady() {
        commandList.add("isready");
        return this;
    }

    public CommandQuery setPosition(String fenString) {
        commandList.add("position fen " + fenString);
        return this;
    }

    public CommandQuery startNewGame() {
        commandList.add("ucinewgame");
        return this;
    }

    public CommandQuery exitTheGame() {
        commandList.add("quit");
        destroyEngineInside();
        return this;
    }

    public CommandQuery go(GoEnum goOption) {
        commandList.add("go " + goOption.getText());
        return this;
    }

    public CommandQuery go(GoEnum goOption, Integer value) {
        commandList.add("go " + goOption.getText() + " " + value);
        return this;
    }

    public boolean isMsgCanBeFoundInLogs(String answerExample) {
        boolean msgFound = false;
        ArrayList<String> answerList;
        answerList = returnMessagesFromEngineResponse();
        if(answerList != null){
            for(String answer : answerList){
                if(answer.contains(answerExample)){
                    msgFound = true;
                    break;
                }
            }
        }
        return msgFound;
    }

    boolean isMsgWasSendToEngine(String answerExample) {
        boolean msgFound = false;
        ArrayList<String> answerList;
        answerList = returnCommandsProcessedByEngine();
        if(answerList != null) {
            for (String answer : answerList) {
                if (answer.contains(answerExample)) {
                    msgFound = true;
                    break;
                }
            }
        }
        return msgFound;
    }



    public ArrayList<String> returnDataFromEngineResponse(boolean responseOrCommand){
        if(resultsOfCommand.size() != 0){
            ArrayList<String> responses = new ArrayList<>();
            for(EngineResponse response : resultsOfCommand){
                if(responseOrCommand)
                    responses.add(response.getResponseFromEngine());
                else
                    responses.add(response.getCommandName());

            }
            return responses;
        }
        return null;
    }


    private void retryWaitOnAnswer(int timeout) throws InterruptedException {
        int retryWait=0;
        while(!isMsgCanBeFoundInLogs("bestmove")) {
            sleep(1000);
            if(retryWait >= timeout){
                commandList.add("quit");
                break;
            }
            retryWait += 1000;
        }
    }


    private void destroyEngineInside(){
        engineHandler.brutallyKillEngine();
    }

}
