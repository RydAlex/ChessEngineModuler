package chess.manager.game.definitions;

import chess.amqp.sender.AMQPSender;
import chess.manager.messages.processors.EloProcessor;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.EngineEloPair;
import chess.engine.processor.core.enginemechanism.FenGenerator;
import chess.manager.messages.processors.VotingProcessor;

import java.util.List;

/**
 * Created by aleksanderr on 17/06/17.
 */
public class FullInsideGameDefiner extends GameDefiner {

    public void playFullActorDepthGameWithDefinedEnginesNames(List<EngineEloPair> engineEloPairs, int depth, TypeOfMessageExtraction type){
        FenGenerator fenGenerator = new FenGenerator(); // GAME NEAR TO END - 2 kings 8/8/5K2/8/8/5k2/8/8 w - -
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
        saveEnginesVotesStats(answer);

        System.gc();
    }

    public void playFullActorTimeoutGameWithDefindedEnginesNames(List<EngineEloPair> engineEloPairs, int timeout, TypeOfMessageExtraction type) {
        FenGenerator fenGenerator = new FenGenerator(); // GAME NEAR TO END - 2 kings 8/8/5K2/8/8/5k2/8/8 w - -
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
        saveEnginesVotesStats(answer);

        System.gc();
    }

    private void extractAndSaveGameResult(ChessJSONObject answer) {
        if(answer != null && !answer.getAnswer().equals("-1")){
            EloProcessor eloProcessor = new EloProcessor();
            eloProcessor.fetchDataAndUpdateElo(answer);
        }
    }

    private void saveEnginesVotesStats(ChessJSONObject answer) {
        if(answer != null && !answer.getAnswer().equals("-1")) {
            VotingProcessor votingProcessor = new VotingProcessor();
            votingProcessor.applyVotesToDatabase(answer);
        }
    }

}
