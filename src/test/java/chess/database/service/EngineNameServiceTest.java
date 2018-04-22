package chess.database.service;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.entities.EngineName;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EngineNameServiceTest {

    @Test
    public void saveEngineNameEntity() {
        EngineNameService service = new EngineNameService();
        EngineName engineName = service.saveEngineNameEntity("tadam", TypeOfMessageExtraction.ELO_SIMPLE, 1500);
        Assert.assertNotEquals(null, engineName.getId());
    }

    @Test
    public void getEngineNameEntity() {
    }

    @Test
    public void getEngineNameEntityWithTypeOfGameFilter() {
    }
}