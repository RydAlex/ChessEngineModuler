package engineprocessor.core.enginemechanism;

/**
 * Class which wait with send next message to engine some milisec from last one receive
 * Created by aleksanderr on 04/05/16.
 */
public class DynamicProcessGuardian {

    private int counterValue = 0;
    private int counter=0;
    private boolean processIsReady = false;


    public DynamicProcessGuardian(int milisec){
        counterValue = milisec;
        createDetector();
    }

    public boolean isProcessReady(){
        return processIsReady;
    }

    public void resetGuardState(){
        counter=0;
    }

    public void startDetector(){
        resetGuardState();
        processIsReady=false;
    }

    private void createDetector(){
        Thread countingThread = new Thread() {
            public void run() {
                while (isAlive()) {
                    while (!processIsReady) {
                        while (counter < counterValue) {
                            try {
                                sleep(1);
                                counter++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        processIsReady = true;
                    }
                }
            }
        };
        countingThread.start();
    }

}
