package chess.manager.game.definitions;

import chess.amqp.sender.AMQPSender;
import chess.manager.data.parsers.EngineEloPairParser;
import chess.utils.name.spy.EngineSearcher;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.EngineEloPair;
import chess.engine.processor.core.enginemechanism.FenGenerator;

import java.util.List;

/**
 * Created by aleksanderr on 25/06/17.
 */
public class SingleMoveGameDefiner extends GameDefiner {

    public String sendSimpleDepthMoveRequestWithDefinedNames(List<String> engineNames,
        int depth, TypeOfMessageExtraction type, String chessboardFen, int sizeOne, int sizeTwo) {
        return sendDistributedDepthMoveRequest(
                EngineEloPairParser.findElosForEngineNamesAndCreateEngineEloPair(engineNames),
                depth,
                1,
                type,
                chessboardFen,
                sizeOne,
                sizeTwo
        );
    }

    public String sendSimpleTimeoutMoveRequestWithDefinedNames(List<String> enginesNames,
        int timeout, TypeOfMessageExtraction type, String chessboardFen, int sizeOne, int sizeTwo){

        return sendDistributedTimeoutMoveRequest(
                EngineEloPairParser.findElosForEngineNamesAndCreateEngineEloPair(enginesNames),
                timeout,
                1,
                type,
                chessboardFen,
                sizeOne,
                sizeTwo
        );
    }

    private String sendDistributedDepthMoveRequest(List<EngineEloPair> engineEloPairs, int depth,
            int distributedCalculation, TypeOfMessageExtraction type, String chessboardFen, int sizeOne, int sizeTwo
    ){
        FenGenerator fenGenerator = new FenGenerator(chessboardFen); //mate board -> "3Q4/8/4kQ2/6K1/8/6P1/8/8 b - -"
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPSender sender = new AMQPSender();
        List<ChessJSONObject> answer = sender.sendMessageWithDepthRule(
                fenStringPositions,
                depth,
                type ,
                true ,
                distributedCalculation,
                getEngineNamesFromEngineEloPair(engineEloPairs),
                engineEloPairs,
                sizeOne,
                sizeTwo
        );
        if(!answer.isEmpty()){
            return answer.get(0).getAnswer();
        }
        return "";
    }

    private String sendDistributedTimeoutMoveRequest(List<EngineEloPair> engineEloPairs,
            int timeout, int distributedCalculation, TypeOfMessageExtraction type, String chessboardFen, int sizeOne, int sizeTwo) {
        FenGenerator fenGenerator = new FenGenerator(chessboardFen);
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPSender sender = new AMQPSender();
        List<ChessJSONObject> answer = sender.sendMessageWithTimeoutRule(
                fenStringPositions,
                timeout,
                type,
                true,
                distributedCalculation,
                getEngineNamesFromEngineEloPair(engineEloPairs),
                engineEloPairs,
                sizeOne,
                sizeTwo
        );
        if (!answer.isEmpty()) {
            return answer.get(0).getAnswer();
        }
        return "";
    }

}
