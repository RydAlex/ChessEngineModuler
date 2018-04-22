package chess.manager.game.definitions;

import chess.amqp.sender.AMQPGameCreatorHelper;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.amqp.message.EngineEloPair;
import chess.engine.processor.core.enginemechanism.FenGenerator;

import java.util.List;

/**
 * Created by aleksanderr on 17/06/17.
 */
public class FullInsideGameDefiner extends GameDefiner {

    public void playFullActorDepthGameWithDefinedEnginesNames(List<EngineEloPair> engineEloPairs,
                                                        int depth, TypeOfMessageExtraction type, int sizeOne){
        FenGenerator fenGenerator = new FenGenerator(); // GAME NEAR TO END - 2 kings 8/8/5K2/8/8/5k2/8/8 w - -
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPGameCreatorHelper sender = new AMQPGameCreatorHelper();
        sender.sendMessageWithDepthRule(
                fenStringPositions,
                depth,
                type ,
                false ,
                getEngineNamesFromEngineEloPair(engineEloPairs),
                engineEloPairs,
                sizeOne
        );
        System.gc();
    }

    public void playFullActorTimeoutGameWithDefindedEnginesNamesAndDefinedSize(List<EngineEloPair> engineEloPairs,
                                            int timeout, TypeOfMessageExtraction type, int sizeOne) {

        FenGenerator fenGenerator = new FenGenerator(); // GAME NEAR TO END - 2 kings 8/8/5K2/8/8/5k2/8/8 w - -
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPGameCreatorHelper sender = new AMQPGameCreatorHelper();
        sender.sendMessageWithTimeoutRule(
                fenStringPositions,
                timeout,
                type,
                false,
                getEngineNamesFromEngineEloPair(engineEloPairs),
                engineEloPairs,
                sizeOne
        );
        System.gc();
    }


}
