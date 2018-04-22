package chess.database.dao;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EloEnginesHistoryDAOTest {

    @Test
    public void findByEngineName() {
        EloEnginesHistoryDAO dao = new EloEnginesHistoryDAO();
        List testOfEngineOne = dao.findByEngineName("Chess1");
        List testOfEngineTwo = dao.findByEngineName("Chess2");
        Assert.assertEquals(testOfEngineOne.size(), 1);
        Assert.assertEquals(testOfEngineTwo.size(), 2);

    }
}