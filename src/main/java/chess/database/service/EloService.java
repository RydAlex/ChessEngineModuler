package chess.database.service;

import chess.database.dao.CurrentEloDAO;
import chess.database.dao.EloGamesHistoryDAO;
import chess.database.dao.EngineNameDAO;
import chess.database.entities.CurrentElo;
import chess.database.entities.EloGamesHistory;
import chess.database.entities.EngineName;
import chess.amqp.message.TypeOfMessageExtraction;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by aleksanderr on 02/07/17.
 */
public class EloService {
//    public static List<Integer> getEloValuesForEngines(List<String> engineNames){
//        return engineNames.stream()
//                .map(EloService::getEloValuesForEngineWithType)
//                .collect(Collectors.toCollection(LinkedList::new));
//    }

    @Transactional
    public Integer getEloValuesForEngineWithType(String engineName, TypeOfMessageExtraction type) {
        CurrentEloDAO currElo = new CurrentEloDAO();
        List<CurrentElo> currentEloList = currElo.findByEngineName(engineName, type);
        CurrentElo currentElo = null;
        EngineName engineNameEntity = null;
        if(currentEloList.isEmpty()){
            //Add new engine to engine name
            EngineNameDAO engineNameDAO = new EngineNameDAO();
            List<EngineName> engineNameEntitiesList = engineNameDAO.findByNameAndType(engineName,type.getTypeOfGame());
            if(engineNameEntitiesList.isEmpty()) {
                engineNameEntity = new EngineName();
                engineNameEntity.setEngineName(engineName);
                engineNameEntity.setTypeOfGameUsedByThatEngine(type.getTypeOfGame());
                engineNameDAO.save(engineNameEntity);
            } else {
                engineNameEntity = engineNameEntitiesList.get(0);
            }
            //Add new current elo
            currentElo = new CurrentElo();
            currentElo.setEloValue(1500);
            currentElo.setEngineNameId(engineNameEntity);
            currElo.save(currentElo);
        } else {
            currentElo = currentEloList.get(0);
        }

        return currentElo.getEloValue();
    }

    @Transactional
    public void updateEloValueForEntity(String engineName, TypeOfMessageExtraction type, Integer oldElo, Integer newElo, Boolean isWin) {
        //Znajdz silnik
        EngineNameDAO engineNameDAO = new EngineNameDAO();
        EngineName engineNameEntity = engineNameDAO.findByNameAndType(engineName, type.getTypeOfGame()).get(0);

        //Wpisz stare Elo do EloGamesHistory
        EloGamesHistoryDAO eloGamesHistoryDAO = new EloGamesHistoryDAO();
        EloGamesHistory eloGamesHistory = new EloGamesHistory();
        eloGamesHistory.setOldElo(oldElo);
        eloGamesHistory.setIsWin(isWin);
        eloGamesHistory.setEngineNameId(engineNameEntity);
        eloGamesHistoryDAO.save(eloGamesHistory);

        //Wpisz nowe Elo do CurrentElo
        CurrentEloDAO currentEloDAO = new CurrentEloDAO();
        CurrentElo currentElo = currentEloDAO.findByEngineName(engineName,type).get(0);
        currentElo.setEloValue(newElo);
        currentEloDAO.edit(currentElo);


    }
}
