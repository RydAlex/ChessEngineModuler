package AMQPManagment.AMQPSender;

import AMQPManagment.AMQPSender.SendImpl.AMQPSender;
import AMQPManagment.utils.EngineSearcher;
import AMQPManagment.utils.TypeOfMessageExtraction;
import AMQPManagment.utils.data.ChessJSONObject;
import engineprocessor.core.enginemechanism.FenGenerator;

import java.util.List;

/**
 * Created by aleksanderr on 25/06/17.
 */
class SingleMoveGameDefiner {
    void createDistributedDepthMoveRequest(int numberOfEngine, int depth, int distributedCalculation, TypeOfMessageExtraction type){
        FenGenerator fenGenerator = new FenGenerator(); //mate board -> "3Q4/8/4kQ2/6K1/8/6P1/8/8 b - -"
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPSender sender = new AMQPSender();
        List<ChessJSONObject> answer = sender.sendMessageWithDepthRule(
                fenStringPositions,
                depth,
                type ,
                true ,
                distributedCalculation,
                EngineSearcher.searchFewRandomEngineNames(numberOfEngine)
        );
        if(!answer.isEmpty()){
            if(answer.get(0).getTypeOfGame().equals(TypeOfMessageExtraction.ELO)){

            }

        }
    }

    void createSimpleDepthMoveRequest(int numberOfEngine, int depth, TypeOfMessageExtraction type) {
        createDistributedDepthMoveRequest(numberOfEngine, depth, 1, type);
    }

    void createDistributedTimeoutMoveRequest(int numberOfEngine, int timeout, int distributedCalculation, TypeOfMessageExtraction type) {
        FenGenerator fenGenerator = new FenGenerator();
        String fenStringPositions = fenGenerator.returnFenStringPositions();

        AMQPSender sender = new AMQPSender();
        List<ChessJSONObject> answer = sender.sendMessageWithTimeoutRule(
                fenStringPositions,
                timeout,
                type,
                true,
                distributedCalculation,
                EngineSearcher.searchFewRandomEngineNames(numberOfEngine)
        );
        if (!answer.isEmpty()) {
            if(answer.get(0).getTypeOfGame().equals(TypeOfMessageExtraction.ELO)){

            }
        }
    }

    void createSimpleTimeoutMoveRequest(int numberOfEngine, int timeout, TypeOfMessageExtraction type){
        createDistributedTimeoutMoveRequest(numberOfEngine, timeout, 1, type);
    }
}
