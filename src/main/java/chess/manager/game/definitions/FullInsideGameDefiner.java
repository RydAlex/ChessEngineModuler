package chess.manager.game.definitions;

import chess.amqp.sender.AMQPGameCreatorHelper;
import chess.engine.processor.core.enginemechanism.FenGenerator;
import chess.utils.ChessClusterBattle;

/**
 * Created by aleksanderr on 17/06/17.
 */
public class FullInsideGameDefiner {

    public static void playGameWithThisCluster(int timeout, ChessClusterBattle clusterBattle) {

        FenGenerator fenGenerator = new FenGenerator(); // GAME NEAR TO END - 2 kings 8/8/5K2/8/8/5k2/8/8 w - -
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPGameCreatorHelper sender = new AMQPGameCreatorHelper();
        sender.sendMessageWithTimeoutRule(fenStringPositions, timeout, clusterBattle);
        System.gc();
    }


}
