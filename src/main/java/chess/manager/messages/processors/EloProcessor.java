package chess.manager.messages.processors;

import chess.database.service.EloService;
import chess.algorithms.elo.EloAlgorithm;
import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.EngineEloPair;
import chess.algorithms.elo.EloGameResultValue;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksanderr on 09/07/17.
 */
public class EloProcessor {
    EloService eloService = new EloService();

    public void fetchDataAndUpdateElo(ChessJSONObject answer) {
        List<String> packPlayNames = createEngineNameEnginesAnswer(answer);
        EngineEloPair firstGroupEngineElo = new EngineEloPair();
        firstGroupEngineElo.setEngineName(packPlayNames.get(0));
        EngineEloPair secondGroupEngineElo = new EngineEloPair();
        secondGroupEngineElo.setEngineName(packPlayNames.get(1));

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

         eloService.updateEloValueForEntity(
                firstGroupEngineElo.getEngineName(),
                answer.getTypeOfGame(),
                firstGroupEngineElo.getEloValue(),
                newRatingOfFirstGroup,
                extractGameResult(answer.getAnswer(), 1).isWin()
        );

        eloService.updateEloValueForEntity(
                secondGroupEngineElo.getEngineName(),
                answer.getTypeOfGame(),
                secondGroupEngineElo.getEloValue(),
                newRatingOfSecondGroup,
                extractGameResult(answer.getAnswer(), 2).isWin()
        );
    }

    public List<String> createEngineNameEnginesAnswer(ChessJSONObject answer){
        List<String> enginesNames = new LinkedList<>();
        String pack_one = "", pack_two = "";
        for(int i=0 ; i<answer.getChessGameName().size() ; i++){
            if(i < answer.getSizeOfEnginesInFight().get("GroupOneSize")){
                if(!pack_one.isEmpty()){
                    pack_one += "_";
                }
                pack_one += answer.getChessGameName().get(i);
            } else {
                if(!pack_two.isEmpty()){
                    pack_two += "_";
                }
                pack_two += answer.getChessGameName().get(i);
            }
        }
        enginesNames.add(pack_one+"1_vs_"+pack_two+"2");
        enginesNames.add(pack_two+"2_vs_"+pack_one+"1");
        return enginesNames;
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
                eloService.getEloValuesForEngineWithType(
                groupEngineElo.getEngineName(),
                answer.getTypeOfGame()
            )
        );
    }

    private EloGameResultValue extractGameResult(String resultOfGame, int groupCalculated){
        Integer result = Integer.parseInt(resultOfGame);
        if(result.equals(0)){
            return EloGameResultValue.DRAW;
        } else if(result.equals(groupCalculated)){
            return EloGameResultValue.WIN;
        } else {
            return EloGameResultValue.LOSE;
        }
    }
}
