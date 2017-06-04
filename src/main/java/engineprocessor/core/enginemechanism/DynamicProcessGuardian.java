package engineprocessor.core.enginemechanism;

/**
 * Class which wait with send next message to engine some milisec from last one receive
 * Created by aleksanderr on 04/05/16.
 */
public class DynamicProcessGuardian {

    Thread countingThread;
    private int counterValue = 0;
    private int counter=0;
    private boolean processIsReadyToTakeAnotherCommand = false;


    public DynamicProcessGuardian(int milisec){
        counterValue = milisec;
        createDetector();
    }

    public boolean isProcessReady(){
        return processIsReadyToTakeAnotherCommand;
    }

    public void resetGuardState(){
        counter=0;
    }

    public void startDetector(){
        resetGuardState();
        processIsReadyToTakeAnotherCommand = false;
    }

    public void killCounter(){
        countingThread.interrupt();
    }

    private void createDetector(){
        countingThread = new Thread() {
            public void run() {
                while (isAlive()) {
                    while (!processIsReadyToTakeAnotherCommand) {
                        while (counter < counterValue) {
                            try {
                                Thread.sleep(10);
                                counter++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        processIsReadyToTakeAnotherCommand = true;
                    }
                }
            }
        };
        countingThread.start();
    }

}
