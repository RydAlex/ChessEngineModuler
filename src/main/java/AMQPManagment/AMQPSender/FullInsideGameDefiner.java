package AMQPManagment.AMQPSender;

import AMQPManagment.AMQPSender.SendImpl.*;
import AMQPManagment.utils.EngineSearcher;
import AMQPManagment.utils.TypeOfMessageExtraction;
import AMQPManagment.utils.data.ChessJSONObject;
import engineprocessor.core.enginemechanism.FenGenerator;

import java.util.List;

/**
 * Created by aleksanderr on 17/06/17.
 */
class FullInsideGameDefiner {
    void createSimpleDepthGame(int numberOfEngine, int depth, TypeOfMessageExtraction type){
        FenGenerator fenGenerator = new FenGenerator("3Q4/8/4kQ2/6K1/8/6P1/8/8 b - -");
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPSender sender = new AMQPSender();
        List<ChessJSONObject> answer = sender.sendMessageWithDepthRule(
                fenStringPositions,
                depth,
                type ,
                true ,
                1,
                EngineSearcher.searchFewRandomEngineNames(numberOfEngine)
        );
        if(!answer.isEmpty()){
            if(answer.get(0).getTypeOfGame().equals(TypeOfMessageExtraction.ELO)){

            }

        }
    }

    void createSimpleTimeoutGame(int numberOfEngine, int timeout, TypeOfMessageExtraction type) {
        FenGenerator fenGenerator = new FenGenerator("3Q4/8/4kQ2/6K1/8/6P1/8/8 b - -");
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPSender sender = new AMQPSender();
        List<ChessJSONObject> answer = sender.sendMessageWithTimeoutRule(
                fenStringPositions,
                timeout,
                type,
                true,
                1,
                EngineSearcher.searchFewRandomEngineNames(numberOfEngine)
        );
        if (!answer.isEmpty()) {
            if(answer.get(0).getTypeOfGame().equals(TypeOfMessageExtraction.ELO)){

            }
        }
    }
}
