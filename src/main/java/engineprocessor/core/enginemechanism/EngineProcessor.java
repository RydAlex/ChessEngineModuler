package engineprocessor.core.enginemechanism;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Class which allow communicate in and out with engine
 * Created by aleksanderr on 26/04/16.
 */

public class EngineProcessor {

    private PrintWriter output;
    private Process engineProcess;
    private CommandQuery commandQuery;
    private boolean readingFromEngine;
    private boolean sendingToEngine;

    private boolean isEngineProcessorWorkFully(){
        return readingFromEngine && sendingToEngine;
    }

    public void brutallyKillEngine(){
        engineProcess.destroy();
    }

    public boolean isEngineStillWork(){
        return engineProcess.isAlive();
    }

    public CommandQuery setEngineConnection(String pathToEngine, int dynamicGuardTimeout) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(pathToEngine);
        engineProcess = pb.start();
        final Scanner input = new Scanner(engineProcess.getInputStream());
        output = new PrintWriter(engineProcess.getOutputStream());

        DynamicProcessGuardian guard = new DynamicProcessGuardian(dynamicGuardTimeout);

        commandQuery = new CommandQuery(guard, this);

        // Receive message
        new Thread() {
            public void run() {
                while (engineProcess.isAlive())
                {
                    readingFromEngine = true;
                    while (input.hasNextLine()) {
                        String resultLine = input.nextLine();
                        //System.out.println(commandQuery.processedCommand + " " + resultLine);
                        commandQuery.setResultOfCommand(resultLine);
                    }
                }
                input.close();
            }
        }.start();

        // Sending message
        new Thread() {
            public void run() {
                while(engineProcess.isAlive())
                {
                    sendingToEngine = true;
                    String command = null;
                    while(command == null){
                        command = commandQuery.getCommand();
                    }
                    output.println(command);
                    output.flush();
                }
                output.close();
            }
        }.start();

        int i=0;
        while (i<=3 && !(isEngineProcessorWorkFully() && commandQuery.isMsgWasSendToEngine("Initialize"))){
            Thread.sleep(1000);
            i++;
        }
        return commandQuery;

    }
}
