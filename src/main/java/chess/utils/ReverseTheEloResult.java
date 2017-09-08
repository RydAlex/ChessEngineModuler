package chess.utils;

import chess.database.dao.CurrentEloDAO;
import chess.database.dao.EloGamesHistoryDAO;
import chess.database.dao.EngineNameDAO;
import chess.database.entities.CurrentElo;
import chess.database.entities.EloGamesHistory;
import chess.database.entities.EngineName;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksanderr on 25/07/17.
 */

public class ReverseTheEloResult {

    public static void main(String ...args){
        EngineNameDAO eNameDao = new EngineNameDAO();
        List<EngineName> engineNameList = eNameDao.findAll();
        engineNameList.forEach(ReverseTheEloResult::calculateNewElosInEngineStats);
        return;
    }

    private static void calculateNewElosInEngineStats(EngineName engineName) {
        EloGamesHistoryDAO eloHistoryDao = new EloGamesHistoryDAO();
        CurrentEloDAO currentEloDao = new CurrentEloDAO();
        List<EloGamesHistory> historyGames = eloHistoryDao.findByEngineName(engineName.getEngineName());
        Integer elo = 1500;
        List<Integer> newValues = new LinkedList<>();
        newValues.add(elo);
        Collections.sort(historyGames, (lhs, rhs) ->  lhs.getId() < rhs.getId() ? -1 : (lhs.getId() > rhs.getId() ) ? 1 : 0);
        for(int historyGameIndex = 0; historyGameIndex<historyGames.size()-1; historyGameIndex++){
            EloGamesHistory gameHistoryRecord1 = historyGames.get(historyGameIndex);
            EloGamesHistory gameHistoryRecord2 = historyGames.get(historyGameIndex + 1);
            Integer difference = gameHistoryRecord2.getOldElo() - gameHistoryRecord1.getOldElo();
            elo = elo - difference;
            newValues.add(elo);
            System.out.println("Elo calculated: " + elo + " Elo db: " + gameHistoryRecord2.getOldElo() + " record id: " + gameHistoryRecord2.getId());
        }
        EloGamesHistory lastGameHistoryRecord = historyGames.get(historyGames.size()-1);
        CurrentElo currentElo = currentEloDao.findByEngineNameAndType(engineName.getEngineName()).get(0);
        Integer difference = currentElo.getEloValue() - lastGameHistoryRecord.getOldElo();
        elo = elo - difference;
        System.out.println("Last elo calculated: " + elo + " Elo db: " + currentElo.getEloValue() + " Id: " + engineName.getId());

        //Change values now and save it all.
        for(int i=0 ; i<historyGames.size() ; i++){
            historyGames.get(i).setOldElo(newValues.get(i));
            if(historyGames.get(i).getIsWin() != null){
                historyGames.get(i).setIsWin(!historyGames.get(i).getIsWin());
            }
            eloHistoryDao.edit(historyGames.get(i));
        }
        currentElo.setEloValue(elo);
        currentEloDao.edit(currentElo);
    }
}
