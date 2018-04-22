package chess.database.service;

import chess.database.dao.EloEnginesHistoryDAO;
import chess.database.entities.EloEnginesHistory;
import chess.database.entities.EngineName;

public class EloEnginesHistoryService {

    public void saveEloHistory(Integer newElo, EngineName engineName ){
        EloEnginesHistoryDAO service = new EloEnginesHistoryDAO();
        EloEnginesHistory engineHistory = new EloEnginesHistory();
        engineHistory.setCurrentElo(newElo);
        engineHistory.setEngineNameId(engineName);
        engineHistory.setOldElo(engineName.getCurrentElo());
        service.save(engineHistory);
    }
}
