package engineprocessor.core;

import engineprocessor.core.enginemechanism.DynamicProcessGuardian;
import org.junit.Test;

/**
 * Created by aleksanderr on 04/05/16.
 */
public class DynamicProcessGuardianTest {


    @Test
    public void isBlockerGuardianWorking() throws Exception {
        for(int i=0; i<50 ; i++){
            DynamicProcessGuardian guard = new DynamicProcessGuardian(200);
            assert !guard.isProcessReady();
            Thread.sleep(50);
            assert !guard.isProcessReady();
            Thread.sleep(300);
            assert guard.isProcessReady();
        }
    }


    @Test
    public void isBlockerGuardianWorkingWithReset() throws Exception {
        DynamicProcessGuardian guard = new DynamicProcessGuardian(200);
        assert !guard.isProcessReady();
        Thread.sleep(100);
        assert !guard.isProcessReady();
        guard.resetGuardState();
        Thread.sleep(100);
        assert !guard.isProcessReady();
        for(int i=0 ; i<50; i++){
            guard.resetGuardState();
            Thread.sleep(100);
            assert !guard.isProcessReady();
        }
        Thread.sleep(300);
        assert guard.isProcessReady();
    }


    @Test
    public void isBlockerGuardianWorkWithStopAndStartItAgain() throws Exception {
        DynamicProcessGuardian guard = new DynamicProcessGuardian(200);
        assert !guard.isProcessReady();
        Thread.sleep(100);
        assert !guard.isProcessReady();
        Thread.sleep(200);
        assert guard.isProcessReady();
        for(int i = 0 ; i<50 ; i++){
            guard.startDetector();
            Thread.sleep(50);
            assert !guard.isProcessReady();
            Thread.sleep(400);
            assert guard.isProcessReady();
        }
    }

    @Test
    public void isBlockerGuardianWorkWithStopAndStartItAgainAndRestartItLater() throws Exception {
        DynamicProcessGuardian guard = new DynamicProcessGuardian(200);
        for(int i=0; i<50 ; i++){
            assert !guard.isProcessReady();
            Thread.sleep(50);
            assert !guard.isProcessReady();
            Thread.sleep(300);
            assert guard.isProcessReady();
            guard.startDetector();
        }
        Thread.sleep(100);
        assert !guard.isProcessReady();
        guard.resetGuardState();
        Thread.sleep(150);
        assert !guard.isProcessReady();
        guard.resetGuardState();
        Thread.sleep(150);
        assert !guard.isProcessReady();
        Thread.sleep(300);
        assert guard.isProcessReady();

    }

}