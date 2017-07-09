package AMQPManagment.AMQPSender.dataFetchers;

import AMQPManagment.DatabaseConnections.Service.EloService;
import AMQPManagment.utils.EloAlgorithm;
import AMQPManagment.utils.data.ChessJSONObject;
import AMQPManagment.utils.data.EngineEloPair;
import AMQPManagment.utils.data.GameResult;

/**
 * Created by aleksanderr on 09/07/17.
 */
public class EloProcessor {
    public void fetchDataAndUpdateElo(ChessJSONObject answer) {
        EngineEloPair firstGroupEngineElo = answer.getChessGamesEloValue().get(0);
        EngineEloPair secondGroupEngineElo = answer.getChessGamesEloValue().get(1);

        getCurrentEloValueForEngineAndSetProperNameOfEngine(firstGroupEngineElo, answer);
        getCurrentEloValueForEngineAndSetProperNameOfEngine(secondGroupEngineElo, answer);

        Integer newRatingOfFirstGroup = EloAlgorithm.calculateRating(
                firstGroupEngineElo.getEloValue(),
                secondGroupEngineElo.getEloValue(),
                extractGameResult(answer.getAnswer(), 1)
            );

        Integer newRatingOfSecondGroup = EloAlgorithm.calculateRating(
                secondGroupEngineElo.getEloValue(),
                firstGroupEngineElo.getEloValue(),
                extractGameResult(answer.getAnswer(), 2)
        );

        EloService.updateEloValueForEntity(
                firstGroupEngineElo.getEngineName(),
                answer.getTypeOfGame(),
                firstGroupEngineElo.getEloValue(),
                newRatingOfFirstGroup,
                extractGameResult(answer.getAnswer(), 1).isWin()
        );

        EloService.updateEloValueForEntity(
                secondGroupEngineElo.getEngineName(),
                answer.getTypeOfGame(),
                secondGroupEngineElo.getEloValue(),
                newRatingOfSecondGroup,
                extractGameResult(answer.getAnswer(), 2).isWin()
        );
    }

    private void getCurrentEloValueForEngineAndSetProperNameOfEngine(EngineEloPair groupEngineElo, ChessJSONObject answer) {
        String nameSufix;
        if(answer.getDepth() != null){
            nameSufix = "_depth_"+answer.getDepth();
        } else{
            nameSufix = "_timeout_"+answer.getTimeout();
        }
        groupEngineElo.setEngineName(groupEngineElo.getEngineName()+nameSufix);

        groupEngineElo.setEloValue(
            EloService.getEloValuesForEngineWithType(
                groupEngineElo.getEngineName(),
                answer.getTypeOfGame()
            )
        );
    }

    private GameResult extractGameResult(String resultOfGame, int groupCalculated){
        Integer result = Integer.parseInt(resultOfGame);
        if(result.equals(0)){
            return GameResult.DRAW;
        } else if(result.equals(groupCalculated)){
            return GameResult.WIN;
        } else {
            return GameResult.LOSE;
        }
    }
}
