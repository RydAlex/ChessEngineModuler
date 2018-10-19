package chess.amqp.sender;

/**
 * Created by aleksanderr on 22/05/17.
 */
import chess.amqp.newAMQP.AMQPSenderImpl;
import chess.utils.ChessClusterBattle;
import chess.utils.settings.Settings;
import chess.utils.json.object.ChessJSONCreator;

public class AMQPGameCreatorHelper {

    public void sendMessageWithTimeoutRule(String fen, int timeout, ChessClusterBattle clusterBattle){

        String message = ChessJSONCreator.createChessJsonWithTimeoutRule(fen, timeout, clusterBattle);
        try {
            AMQPSenderImpl.sendMessage(Settings.getChessProcessingQueueName(), message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Something goes wrong");
        }
    }
}