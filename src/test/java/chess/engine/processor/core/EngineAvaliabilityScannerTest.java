package chess.engine.processor.core;

import chess.engine.processor.core.enginemechanism.EngineAvailabilityScanner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by aleksanderr on 18/03/17.
 */
public class EngineAvaliabilityScannerTest {

    private EngineAvailabilityScanner engineAvailability;

    @Before
    public void setUp(){
        engineAvailability = EngineAvailabilityScanner.getInstance();
    }

    @Test
    public void isTestReturnProperMapOfEngineWithTheirPath(){
        Assert.assertEquals(16, engineAvailability.returnMapOfEnginePaths().size());
    }

    @Test
    public void isTestReturnProperListOfEngine(){
        Assert.assertEquals(16, engineAvailability.returnListOfNames().size());
    }

}