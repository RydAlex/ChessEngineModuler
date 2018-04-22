package chess.database.dao;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EngineNameDAOTest {

    @Test
    public void findByName() {
        EngineNameDAO dao = new EngineNameDAO();
        List engineName = dao.findByName("Chess1");
        Assert.assertEquals(engineName.size(), 1);
    }

    @Test
    public void findByNameAndType() {
        EngineNameDAO dao = new EngineNameDAO();
        List engineName = dao.findByNameAndType("Chess3", "ELO_GAMES");
        Assert.assertEquals(engineName.size(), 1);
    }

    @Test
    public void findAll() {
        EngineNameDAO dao = new EngineNameDAO();
        List engineName = dao.findAll();
        Assert.assertEquals(engineName.size(), 3);
    }
}