package AMQPManagment.DatabaseConnections.Service;

import AMQPManagment.DatabaseConnections.DAO.CurrentEloDAO;
import AMQPManagment.DatabaseConnections.DAO.EloGamesHistoryDAO;
import AMQPManagment.DatabaseConnections.DAO.EngineNameDAO;
import AMQPManagment.DatabaseConnections.Entities.CurrentElo;
import AMQPManagment.DatabaseConnections.Entities.EloGamesHistory;
import AMQPManagment.DatabaseConnections.Entities.EngineName;
import AMQPManagment.utils.TypeOfMessageExtraction;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aleksanderr on 02/07/17.
 */
public class EloService {
    public static List<Integer> getEloValuesForEngines(List<String> engineNames){
        return engineNames.stream()
                .map(EloService::getEloValuesForEngine)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional
    public static Integer getEloValuesForEngine(String engineName) {
        CurrentEloDAO currElo = new CurrentEloDAO();
        List<CurrentElo> currentEloList = currElo.findByEngineName(engineName);
        CurrentElo currentElo = null;
        EngineName engineNameEntity = null;
        if(currentEloList.isEmpty()){
            //Add new engine to engine name
            EngineNameDAO engineNameDAO = new EngineNameDAO();
            List<EngineName> engineNameEntitiesList = engineNameDAO.findByName(engineName);
            if(engineNameEntitiesList.isEmpty()) {
                engineNameEntity = new EngineName();
                engineNameEntity.setEngineName(engineName);
                engineNameEntity.setTypeOfGameUsedByThatEngine(TypeOfMessageExtraction.ELO_SIMPLE.getTypeOfGame());
                engineNameDAO.save(engineNameEntity);
            } else {
                engineNameEntity = engineNameEntitiesList.get(0);
            }
            //Add new current elo
            currentElo = new CurrentElo();
            currentElo.setEloValue(1000);
            currentElo.setEngineNameId(engineNameEntity);
            currElo.save(currentElo);
        } else {
            currentElo = currentEloList.get(0);
        }

        return currentElo.getEloValue();
    }

    public static void updateEloValueForEntity(String engineName, Integer oldElo, Integer newElo, Boolean isWin) {
        //Znajdz silnik
        EngineNameDAO engineNameDAO = new EngineNameDAO();
        EngineName engineNameEntity = engineNameDAO.findByName(engineName).get(0);

        //Wpisz stare Elo do EloGamesHistory
        EloGamesHistoryDAO eloGamesHistoryDAO = new EloGamesHistoryDAO();
        EloGamesHistory eloGamesHistory = new EloGamesHistory();
        eloGamesHistory.setOldElo(oldElo);
        eloGamesHistory.setIsWin(isWin);
        eloGamesHistory.setEngineNameId(engineNameEntity);
        eloGamesHistoryDAO.save(eloGamesHistory);

        //Wpisz nowe Elo do CurrentElo
        CurrentEloDAO currentEloDAO = new CurrentEloDAO();
        CurrentElo currentElo = currentEloDAO.findByEngineName(engineName).get(0);
        currentElo.setEloValue(newElo);
        currentEloDAO.edit(currentElo);

    }
}
