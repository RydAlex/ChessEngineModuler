package chess.amqp.sender;

/**
 * Created by aleksanderr on 22/05/17.
 */
import chess.amqp.message.TypeOfMessageExtraction;
import chess.utils.json.object.ChessJSONCreator;
import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.EngineEloPair;

import java.util.LinkedList;
import java.util.List;

public class AMQPSender {

    public List<ChessJSONObject> sendMessageWithDepthRule(String fen, int depth, TypeOfMessageExtraction typeOfGame, boolean isSingleMove,
                                                          int howManyTimeCalculate, List<String> engineNames, List<EngineEloPair> engineEloPairs,
                                                            int sizeOne, int sizeTwo){

        String json = ChessJSONCreator.createChessJsonWithDepthRule(fen, depth, typeOfGame, isSingleMove, engineNames, engineEloPairs, sizeOne, sizeTwo);
        List<String> jsonsToSend = new LinkedList<>();
        for(int i=1; i<=howManyTimeCalculate; i++){
            jsonsToSend.add(json);
        }
        try {
            return AMQPSenderConnImpl.sendMessages(jsonsToSend);
        } catch (Exception e) {
            System.out.println("Something goes wrong");
        }
        return null;
    }

    public List<ChessJSONObject> sendMessageWithTimeoutRule(String fen, int timeout, TypeOfMessageExtraction typeOfGame, boolean isSingleMove,
                                                            int howManyTimeCalculate, List<String> engineNames, List<EngineEloPair> eloValues,
                                                            int sizeOne, int sizeTwo){

        String json = ChessJSONCreator.createChessJsonWithTimeoutRule(fen, timeout, typeOfGame, isSingleMove, engineNames, eloValues, sizeOne, sizeTwo);
        List<String> jsonsToSend = new LinkedList<>();
        for(int i=1; i<=howManyTimeCalculate; i++){
            jsonsToSend.add(json);
        }
        try {
            return AMQPSenderConnImpl.sendMessages(jsonsToSend);
        } catch (Exception e) {
            System.out.println("Something goes wrong");
        }
        return null;
    }
}