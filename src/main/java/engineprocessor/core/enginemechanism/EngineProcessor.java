package engineprocessor.core.enginemechanism;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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

    public boolean isEngineStillWork(){
        return engineProcess.isAlive();
    }

    public CommandQuery setEngineConnection(String pathToEngine, int dynamicGuardTimeout) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(pathToEngine);
        engineProcess = pb.start();
        final Scanner input = new Scanner(engineProcess.getInputStream());
        output = new PrintWriter(engineProcess.getOutputStream());

        commandQuery = new CommandQuery();
        DynamicProcessGuardian guard = new DynamicProcessGuardian(dynamicGuardTimeout);

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
                        try {
                            sleep(1);
                            guard.resetGuardState();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
                    while(guard.isProcessReady()){
                        String command = null;
                        while(command == null){
                            command = commandQuery.getCommand();
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        output.println(command);
                        output.flush();
                        guard.startDetector();
                    };
                }
                output.close();
            }
        }.start();

        while (!(isEngineProcessorWorkFully() && commandQuery.isMsgWasSendToEngine("Initialize"))){
            Thread.sleep(1000);
        }
        return commandQuery;

    }
}
