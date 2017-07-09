package AMQPManagment.AMQPSender.sendImpl;

/**
 * Created by aleksanderr on 22/05/17.
 */
import AMQPManagment.utils.TypeOfMessageExtraction;
import AMQPManagment.utils.chessJSONParsers.ChessJSONCreator;
import AMQPManagment.utils.data.ChessJSONObject;
import AMQPManagment.utils.data.EngineEloPair;

import java.util.LinkedList;
import java.util.List;

public class AMQPSender {

    public List<ChessJSONObject> sendMessageWithDepthRule(String fen, int depth, TypeOfMessageExtraction typeOfGame, boolean isSingleMove,
                                                          int howManyTimeCalculate, List<String> engineNames, List<EngineEloPair> engineEloPairs){

        String json = ChessJSONCreator.createChessJsonWithDepthRule(fen, depth, typeOfGame, isSingleMove, engineNames, engineEloPairs);
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
                                                            int howManyTimeCalculate, List<String> engineNames, List<EngineEloPair> eloValues){

        String json = ChessJSONCreator.createChessJsonWithTimeoutRule(fen, timeout, typeOfGame, isSingleMove, engineNames, eloValues);
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