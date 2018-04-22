package chess.database.service;

import chess.database.dao.BattleDAO;
import chess.database.entities.Battle;
import chess.database.entities.EngineName;

public class BattleService {

    public Battle saveBattle(EngineName engineNameOne, EngineName engineNameTwo,
                                    String gameplayString, Integer whoWin){
        BattleDAO dao = new BattleDAO();
        Battle battle = new Battle();
        battle.setEngineNameOneId(engineNameOne);
        battle.setEngineNameTwoId(engineNameTwo);
        battle.setWinOfFirst(isWin(whoWin));
        battle.setGameplayString(gameplayString);
        dao.save(battle);
        return battle;
    }

    private Boolean isWin(Integer whoWin) {
        if(whoWin == 1){
            return true;
        } else if (whoWin == 2){
            return false;
        } else {
           return null;
        }
    }
}
