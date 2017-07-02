package AMQPManagment.DatabaseConnections.DAO;

import AMQPManagment.DatabaseConnections.Entities.DepthWith2MoreEloUseWinLose;
import org.junit.Test;

/**
 * Created by aleksanderr on 18/06/17.
 */
public class GameTypeDAOTest {

    @Test
    public void isGameTypeReturnProperValues() {
        DepthWith2MoreEloUseWinLoseDAO eloGamesHistoryDao = new DepthWith2MoreEloUseWinLoseDAO();

        DepthWith2MoreEloUseWinLose gamesHistory = eloGamesHistoryDao.find(1);
        System.out.println(gamesHistory.getId());
        System.out.println(gamesHistory.getTimestamp());
        System.out.println(gamesHistory.getIsWin());
        System.out.println(gamesHistory.getEngineNameId());
        System.out.println("---------------------------------------------");
        gamesHistory = eloGamesHistoryDao.find(2);
        System.out.println(gamesHistory.getId());
        System.out.println(gamesHistory.getTimestamp());
        System.out.println(gamesHistory.getIsWin());
        System.out.println(gamesHistory.getEngineNameId());

        gamesHistory = new DepthWith2MoreEloUseWinLose();
        gamesHistory.setEngineNameId(1);
        gamesHistory.setIsWin(false);
        eloGamesHistoryDao.save(gamesHistory);
        System.out.println("---------------------------------------------2");
        eloGamesHistoryDao.save(gamesHistory);

        eloGamesHistoryDao.closeConnection();

    }
}