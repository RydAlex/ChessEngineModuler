package engineprocessor.core.enginemechanism;

import engineprocessor.core.utils.EngineResponse;
import engineprocessor.core.utils.enums.GoEnum;

import java.util.*;

import static java.lang.Thread.sleep;

/**
 * A list of command which can be send
 * Created by aleksanderr on 27/04/16.
 */
public class CommandQuery {

    String processedCommand = "Initialize";
    private LinkedList<String> commandList = new LinkedList<>();
    private ArrayList<EngineResponse> resultsOfCommand = new ArrayList<>();



    public boolean isListOfCommandEmpty(){
        return commandList.size() != 0;
    }

    String getCommand() {
        try {
            if (!commandList.isEmpty()) {
                processedCommand = commandList.getLast();
                commandList.removeLast();
                return processedCommand;
            }
        } catch (Exception e) {
            // something weird happened....
        }
        return null;
    }

    public void clearMessagesInLogs() {
        resultsOfCommand.clear();
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


    public String returnMoveWhichEngineFound(){
        String moveFound = "";
        ArrayList<String> answerList;
        try {
            retryWaitOnAnswer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        answerList = returnMessagesFromEngineResponse();
        if(answerList != null){
            for(String answer : answerList){
                if(answer.contains("bestmove")){
                    String[] answerParts = answer.split(" ");
                    moveFound = answerParts[1];
                    break;
                }
            }
        }
        return moveFound;
    }

    public CommandQuery getChessEngineInformation() {
        commandList.addFirst("uci");
        return this;
    }

    public CommandQuery showActualLookOfGame() {
        commandList.addFirst("d");
        return this;
    }

    public CommandQuery isEngineReady() {
        commandList.addFirst("isready");
        return this;
    }

    public CommandQuery setPosition(String fenString) {
        commandList.addFirst("position fen " + fenString);
        return this;
    }

    public CommandQuery startNewGame() {
        commandList.addFirst("ucinewgame");
        return this;
    }

    public CommandQuery exitTheGame() {
        commandList.addFirst("quit");
        return this;
    }

    public CommandQuery go(GoEnum goOption) {
        commandList.addFirst("go " + goOption.getText());
        return this;
    }

    public CommandQuery go(GoEnum goOption, Integer value) {
        commandList.addFirst("go " + goOption.getText() + " " + value);
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

    public boolean isMsgWasSendToEngine(String answerExample) {
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
        ArrayList<EngineResponse> resultList = (ArrayList<EngineResponse>) resultsOfCommand.clone();
        if(resultList.size() != 0){
            ArrayList<String> responses = new ArrayList<>();
            for(EngineResponse response : resultList){
                if(responseOrCommand)
                    responses.add(response.getResponseFromEngine());
                else
                    responses.add(response.getCommandName());

            }
            return responses;
        }
        return null;
    }


    private void retryWaitOnAnswer() throws InterruptedException {
        int retryWait=0;
        while(!isMsgCanBeFoundInLogs("bestmove")) {
            sleep(1000);
            if(retryWait>=15){
                break;
            }
            retryWait++;
        }
    }
}
