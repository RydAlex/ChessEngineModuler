package chess.amqp.sender;

/**
 * Created by aleksanderr on 22/05/17.
 */
import chess.amqp.message.TypeOfMessageExtraction;
import chess.amqp.newAMQP.AMQPSenderImpl;
import chess.utils.settings.Settings;
import chess.utils.json.object.ChessJSONCreator;
import chess.amqp.message.EngineEloPair;
import java.util.List;

public class AMQPGameCreatorHelper {
    public void sendMessageWithDepthRule(String fen, int depth, TypeOfMessageExtraction typeOfGame, boolean isSingleMove,
                                         List<String> engineNames, List<EngineEloPair> engineEloPairs, int sizeOne){

        String message = ChessJSONCreator.createChessJsonWithDepthRule(fen, depth, typeOfGame, isSingleMove, engineNames, engineEloPairs, sizeOne);
        try {
            AMQPSenderImpl.sendMessage(Settings.getChessProcessingQueueName(), message);
        } catch (Exception e) {
            System.out.println("Something goes wrong");
        }
    }

    public void sendMessageWithTimeoutRule(String fen, int timeout, TypeOfMessageExtraction typeOfGame, boolean isSingleMove,
                                                    List<String> engineNames, List<EngineEloPair> eloValues, int sizeOne){

        String message = ChessJSONCreator.createChessJsonWithTimeoutRule(fen, timeout, typeOfGame, isSingleMove, engineNames, eloValues, sizeOne);
        try {
            AMQPSenderImpl.sendMessage(Settings.getChessProcessingQueueName(), message);
        } catch (Exception e) {
            System.out.println("Something goes wrong");
        }
    }
}