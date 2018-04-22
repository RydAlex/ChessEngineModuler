package chess.database.dao;

import chess.database.entities.Battle;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BattleDAOTest {

    @Test
    public void fetchIfOneOfEnginesHaveNameAs() {
        BattleDAO dao = new BattleDAO();
        List<Battle> list = dao.fetchIfOneOfEnginesHaveNameAs("Chess1");
        Assert.assertEquals(list.size(), 3);
    }

    @Test
    public void fetchOnlyWinEngines() {
        BattleDAO dao = new BattleDAO();
        List<Battle> list = dao.fetchOnlyWinEngines();
        Assert.assertEquals(list.size(), 2);
    }

    @Test
    public void fetchOnlyDrawEngines() {
        BattleDAO dao = new BattleDAO();
        List<Battle> list = dao.fetchOnlyDrawEngines();
        Assert.assertEquals(list.size(), 1);
    }


    @Test
    public void fetchOnlyLoseEngines() {
        BattleDAO dao = new BattleDAO();
        List<Battle> list = dao.fetchOnlyLoseEngines();
        Assert.assertEquals(list.size(), 1);
    }
}