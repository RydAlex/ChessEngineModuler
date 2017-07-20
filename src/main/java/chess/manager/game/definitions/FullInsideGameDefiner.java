package chess.manager.game.definitions;

import chess.amqp.sender.AMQPSender;
import chess.manager.messages.processors.EloProcessor;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.EngineEloPair;
import chess.engine.processor.core.enginemechanism.FenGenerator;

import java.util.List;

/**
 * Created by aleksanderr on 17/06/17.
 */
public class FullInsideGameDefiner extends GameDefiner {

    public void playFullActorDepthGameWithDefinedEnginesNames(List<EngineEloPair> engineEloPairs, int depth, TypeOfMessageExtraction type){
        FenGenerator fenGenerator = new FenGenerator(); //mate board -> "3Q4/8/4kQ2/6K1/8/6P1/8/8 b - -"
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPSender sender = new AMQPSender();
        ChessJSONObject answer = sender.sendMessageWithDepthRule(
                fenStringPositions,
                depth,
                type ,
                false ,
                1,
                getEngineNamesFromEngineEloPair(engineEloPairs),
                engineEloPairs
        ).get(0);
        System.gc();

        extractAndSaveGameResult(answer);

        System.gc();
    }

    public void playFullActorTimeoutGameWithDefindedEnginesNames(List<EngineEloPair> engineEloPairs, int timeout, TypeOfMessageExtraction type) {
        FenGenerator fenGenerator = new FenGenerator();
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPSender sender = new AMQPSender();
        ChessJSONObject answer = sender.sendMessageWithTimeoutRule(
                fenStringPositions,
                timeout,
                type,
                false,
                1,
                getEngineNamesFromEngineEloPair(engineEloPairs),
                engineEloPairs
        ).get(0);
        System.gc();

        extractAndSaveGameResult(answer);

        System.gc();
    }


    private void extractAndSaveGameResult(ChessJSONObject answer) {
        if(answer != null && !answer.getAnswer().equals("-1")){
            if(answer.getTypeOfGame().equals(TypeOfMessageExtraction.ELO_SIMPLE)) {
                EloProcessor eloProcessor = new EloProcessor();
                eloProcessor.fetchDataAndUpdateElo(answer);
            } else if(answer.getTypeOfGame().equals(TypeOfMessageExtraction.RANDOM)) {
                EloProcessor eloProcessor = new EloProcessor();
                eloProcessor.fetchDataAndUpdateElo(answer);
            }
        }
    }

}
