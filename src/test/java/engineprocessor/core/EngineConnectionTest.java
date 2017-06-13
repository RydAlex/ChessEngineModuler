package engineprocessor.core;

import engineprocessor.core.enginemechanism.CommandQuery;
import engineprocessor.core.enginemechanism.EngineAvailabilityScanner;
import engineprocessor.core.enginemechanism.EngineProcessor;
import engineprocessor.core.enginemechanism.FenGenerator;
import engineprocessor.core.utils.enums.GoEnum;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Test for all main.engineprocessor.core.engines deployed at app
 * Created by aleksanderr on 17/04/16.
 */

public class EngineConnectionTest {

    private static int unitTestTimeout = 5000;

    private static ArrayList<String> enginesPath = new ArrayList<>();

    @BeforeClass
    public static void classSetUp(){
        for(Map.Entry<String, String> entry: EngineAvailabilityScanner.getInstance().returnMapOfEnginePaths().entrySet()) {
            enginesPath.add(entry.getValue());
        }

    }

    @Test
    public void canInitialize() throws Exception {
        for(String enginePath : enginesPath){

            EngineProcessor er = new EngineProcessor();
            CommandQuery commandMenu = er.setEngineConnection(enginePath);
            boolean msgFound;
            commandMenu.getChessEngineInformation();
            sleep(unitTestTimeout);
            msgFound = commandMenu.isMsgCanBeFoundInLogs("id name");
            assert msgFound;
            commandMenu.clearMessagesInLogs();
            commandMenu.exitTheGame();
        }
    }

//
//    @Test
//    public void canSetNewOption() throws Exception {
//        EngineProcessor er = EngineProcessor.getInstance();
//        CommandQuery commandMenu = er.setEngineConnection(enginePath);
//        commandMenu.startNewGame();
//        Thread.sleep(unitTestTimeout);
//    }
//

    @Test
    public void canExitAfterThreeSecond() throws Exception {
        for(String enginePath : enginesPath) {
            EngineProcessor er = new EngineProcessor();
            CommandQuery commandMenu = er.setEngineConnection(enginePath);
            commandMenu.exitTheGame();
            Thread.sleep(1000);
            assert !er.isEngineStillWork();
            commandMenu.clearMessagesInLogs();
        }
    }

    @Test
    public void canDebug() throws Exception {

        int counter=0;
        for(String enginePath : enginesPath) {
            EngineProcessor er = new EngineProcessor();
            CommandQuery commandMenu = er.setEngineConnection(enginePath);
            commandMenu.showActualLookOfGame();
            sleep(unitTestTimeout);
            boolean msgFound = commandMenu.isMsgCanBeFoundInLogs("+---+---+---+---+---+---+---+---+");
            if(msgFound) {
                counter++;
            }
            commandMenu.exitTheGame();
        }
        System.out.println(" \n\n System which support debug: " + counter + "/" + enginesPath.size());
    }

    @Test
    public void playFastTimeGame() throws Exception {
        for(String enginePath : enginesPath) {
            System.out.println(enginePath);
            EngineProcessor er = new EngineProcessor();
            CommandQuery commandMenu = er.setEngineConnection(enginePath);
            commandMenu.getChessEngineInformation()
                    .go(GoEnum.searchInTime, 500);
            while (commandMenu.isListOfCommandHaveElements()) {
                sleep(10);
            }
            String msgFound = commandMenu.returnMoveWhichEngineFound(500);
            System.out.println(msgFound);
            commandMenu.exitTheGame();
            assert msgFound.length() == 4;
            commandMenu.clearMessagesInLogs();

        }
    }



    //Proper sequence to get answer:
    // UCI
    // ucinewgame
    // position fen r1k4r/p2nb1p1/2b4p/1p1n1p2/2PP4/3Q1NB1/1P3PPP/R5K1 b - c3 0 19
    // go movetime 5000

    @Test
    public void playProperTypeOfGame() throws Exception {
        int timeout = 5000;
        for(String enginePath : enginesPath) {
            EngineProcessor er = new EngineProcessor();
            CommandQuery commandMenu = er.setEngineConnection(enginePath);
            commandMenu
                    .getChessEngineInformation()                // uci
                    .setPosition("r1k4r/p2nb1p1/2b4p/1p1n1p2/2PP4/3Q1NB1/1P3PPP/R5K1 b - c3 0 19") //position Fen ....
                    .go(GoEnum.searchInTime, timeout);             // go movetime 5000
            while (commandMenu.isListOfCommandHaveElements()) {
                sleep(10);
            }
            String msgFound = commandMenu.returnMoveWhichEngineFound(timeout);
            commandMenu.exitTheGame();
            System.out.println(msgFound);
            assert msgFound.length() == 4 || msgFound.length() == 5;
            commandMenu.clearMessagesInLogs();
        }
    }

    @Test
    public void playDepthTypeOfGame3() throws Exception {
        for(String enginePath : enginesPath) {
            EngineProcessor er = new EngineProcessor();
            CommandQuery commandMenu = er.setEngineConnection(enginePath);
            commandMenu
                    .getChessEngineInformation()                // uci
                    .setPosition("8/8/8/8/K7/8/6p1/7k b - -") //position Fen ....
                    .go(GoEnum.searchDepth, 3);             // go depth 8
            while (commandMenu.isListOfCommandHaveElements()) {
                sleep(10);
            }
            String msgFound = commandMenu.returnMoveWhichEngineFound();
            commandMenu.exitTheGame();
            assert msgFound.length() == 4 || msgFound.length() == 5;
            commandMenu.clearMessagesInLogs();
        }
    }

    @Test
    public void playDepthTypeOfGame5() throws Exception {
        for(String enginePath : enginesPath) {
            EngineProcessor er = new EngineProcessor();
            CommandQuery commandMenu = er.setEngineConnection(enginePath);
            commandMenu
                    .getChessEngineInformation()                // uci
                    .setPosition("8/8/8/8/K7/8/6p1/7k b - -") //position Fen ....
                    .go(GoEnum.searchDepth, 5);             // go depth 8
            while (commandMenu.isListOfCommandHaveElements()) {
                sleep(10);
            }
            String msgFound = commandMenu.returnMoveWhichEngineFound();
            commandMenu.exitTheGame();
            assert msgFound.length() == 4 || msgFound.length() == 5;
            commandMenu.clearMessagesInLogs();
        }
    }

    @Test
    public void playDepthTypeOfGame7() throws Exception {
        for(String enginePath : enginesPath) {
            EngineProcessor er = new EngineProcessor();
            CommandQuery commandMenu = er.setEngineConnection(enginePath);
            commandMenu
                    .getChessEngineInformation()                // uci
                    .setPosition("8/8/8/8/K7/8/6p1/7k b - -") //position Fen ....
                    .go(GoEnum.searchDepth, 7);             // go depth 8
            while (commandMenu.isListOfCommandHaveElements()) {
                sleep(10);
            }
            String msgFound = commandMenu.returnMoveWhichEngineFound();
            commandMenu.exitTheGame();
            System.out.println(msgFound);
            assert msgFound.length() == 4 || msgFound.length() == 5;
            commandMenu.clearMessagesInLogs();
        }
    }



}