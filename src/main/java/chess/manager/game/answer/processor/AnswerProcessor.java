package chess.manager.game.answer.processor;

import chess.algorithms.elo.EloAlgorithm;
import chess.algorithms.elo.EloGameResultValue;
import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.database.entities.EngineName;
import chess.database.service.BattleService;
import chess.database.service.EloEnginesHistoryService;
import chess.database.service.EngineNameService;
import chess.utils.parsing.objects.EngineNameUtil;

import java.util.List;

public class AnswerProcessor {
    private static EloEnginesHistoryService eloEnginesHistoryService = new EloEnginesHistoryService();
    private static EngineNameService engineNameService = new EngineNameService();
    private static BattleService battleService = new BattleService();

    public static synchronized void processAnswer(ChessJSONObject chessJSONObject) {
        if(!chessJSONObject.getAnswer().equals("-1")){
            String engineNameOneString = EngineNameUtil.constructClusterNameFromEngineName(getFirstGroupOfEngine(chessJSONObject));
            String engineNameTwoString = EngineNameUtil.constructClusterNameFromEngineName(getSecondGroupOfEngine(chessJSONObject));
            TypeOfMessageExtraction type = chessJSONObject.getTypeOfGame();

            EngineName engineNameOne = fetchEngineFromDbOrCreateNewRegistry(engineNameOneString, type);
            EngineName engineNameTwo = fetchEngineFromDbOrCreateNewRegistry(engineNameTwoString, type);

            int newEloOne = EloAlgorithm.calculateRating(engineNameOne.getCurrentElo(),
                                                            engineNameTwo.getCurrentElo(), isWin(1, chessJSONObject));
            int newEloTwo = EloAlgorithm.calculateRating(engineNameTwo.getCurrentElo(),
                                                            engineNameOne.getCurrentElo(), isWin(2, chessJSONObject));

            eloEnginesHistoryService.saveEloHistory(newEloOne, engineNameOne);
            eloEnginesHistoryService.saveEloHistory(newEloTwo, engineNameTwo);

            engineNameOne.setCurrentElo(newEloOne);
            engineNameTwo.setCurrentElo(newEloTwo);

            engineNameService.saveEngineNameEntity(engineNameOne);
            engineNameService.saveEngineNameEntity(engineNameTwo);

            battleService.saveBattle(engineNameOne,engineNameTwo, chessJSONObject.getFenMovesInGame(), chessJSONObject.takeAnswerAsInteger());
        }
    }

    private static EloGameResultValue isWin(int forWhichGroup, ChessJSONObject chessJSONObject) {
        if(chessJSONObject.takeAnswerAsInteger() == 0){
            return EloGameResultValue.DRAW;
        }
        if(chessJSONObject.takeAnswerAsInteger() == forWhichGroup){
            return EloGameResultValue.WIN;
        } else {
            return EloGameResultValue.LOSE;
        }
    }

    private synchronized static EngineName fetchEngineFromDbOrCreateNewRegistry(String engineNameOneString, TypeOfMessageExtraction type) {
        List<EngineName> engineNameList = engineNameService.getEngineNameEntity(engineNameOneString);
        if(engineNameList.isEmpty()){
            return engineNameService.saveEngineNameEntity(engineNameOneString, type, 1500);
        } else {
            return engineNameList.get(0);
        }
    }

    private static List<String> getFirstGroupOfEngine(ChessJSONObject chessJSONObject) {
        return chessJSONObject.getChessGameName().subList(0, chessJSONObject.getSizeOfEnginesInFirstGroup());
    }

    private static List<String> getSecondGroupOfEngine(ChessJSONObject chessJSONObject) {
        return chessJSONObject.getChessGameName().subList(chessJSONObject.getSizeOfEnginesInFirstGroup(), chessJSONObject.getChessGameName().size());
    }

}
