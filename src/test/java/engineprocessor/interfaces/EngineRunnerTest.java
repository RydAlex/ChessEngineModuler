package engineprocessor.interfaces;

import engineprocessor.core.enginemechanism.FenGenerator;
import engineprocessor.core.utils.enums.CommandEnum;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by aleksanderr on 19/03/17.
 */
public class EngineRunnerTest {
    @Test
    public void isInterfaceCanReturnEngineNames() throws Exception {
        EngineRunner engineRunner = new EngineRunnerImpl();
        List<String> engineNames = engineRunner.getEngineNames();
        Assert.assertEquals("glaurung",engineNames.get(0));
    }

    @Test
    public void isInterfaceCanRunEngineWithIsReadyCommand() throws Exception {
        EngineRunner engineRunner = new EngineRunnerImpl();
        List<String> engineNames = engineRunner.getEngineNames();
        assertEquals("Glaurung 2.2.  Copyright (C) 2004-2008 Tord Romstad. \n",
                engineRunner.RunEngineWithCommand(engineNames.get(0), CommandEnum.NEW_GAME));
    }

    @Test
    public void isInterfaceCanRunEngineWithGoTimeoutCommand() throws Exception {
        for(int i=0; i<10; i++){
            EngineRunner engineRunner = new EngineRunnerImpl();
            List<String> engineNames = engineRunner.getEngineNames();
            String decision = engineRunner.RunEngineWithGoTimeoutCommand(engineNames.get(0),
                    new FenGenerator().returnFenStringPositions(),
                    5000);
            Assert.assertEquals("g1f3",decision);

        }
    }

    @Test
    public void isInterfaceCanRunEngineWithGoDepthCommand() throws Exception {
        for (int i = 0; i < 10; i++) {
            EngineRunner engineRunner = new EngineRunnerImpl();
            List<String> engineNames = engineRunner.getEngineNames();
            String decision = engineRunner.RunEngineWithGoDepthCommand(engineNames.get(0),
                    new FenGenerator().returnFenStringPositions(),
                    8);
            Assert.assertEquals("g1f3",decision);
        }
    }
}