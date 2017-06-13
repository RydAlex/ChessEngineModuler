package AMQPManagment.utils;

import AMQPManagment.utils.data.ChessJSONObject;
import engineprocessor.core.enginemechanism.FenGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by aleksanderr on 11/06/17.
 */
@Slf4j
public class MessageExtractor {

    public static String getMoveFromEngineResults(TypeOfMessageExtraction type, List<ChessJSONObject> chessObjects){
        String answer = null;
        log.info("Below are answers from engines:");
        for(ChessJSONObject obj : chessObjects){
            log.info(obj.getChessGameName() + " " + obj.getAnswer());
        }
        chessObjects = removeObjectsWhichHaveLosingMessage(chessObjects);
        if(type.equals(TypeOfMessageExtraction.RANDOM)){
            answer = extractMessageInRandomWay(chessObjects);
        } else if(type.equals(TypeOfMessageExtraction.ELO)) {
            answer = extractMessageinEloWay(chessObjects);
        } else if(type.equals(TypeOfMessageExtraction.POSITION)) {
            answer = extractMessageInPositionWay(chessObjects);
        }
        return answer;
    }

    private static String extractMessageInRandomWay(List<ChessJSONObject> chessObject) {
        if(chessObject.size() > 0){
            return chessObject.get(new Random().nextInt(chessObject.size())).getAnswer();
        }
        return null;
    }

    private static String extractMessageInPositionWay(List<ChessJSONObject> chessObject) {
        return null;
    }

    private static String extractMessageinEloWay(List<ChessJSONObject> chessObject) {
        return null;
    }


    private static List<ChessJSONObject> removeObjectsWhichHaveLosingMessage(List<ChessJSONObject> chessObjects) {
        FenGenerator fenGen = new FenGenerator();
        LinkedList<ChessJSONObject> objectsToRemove = new LinkedList<>();
        for(ChessJSONObject obj : chessObjects) {
            if (fenGen.isMoveACheckmate(obj.getAnswer())) {
                objectsToRemove.add(obj);
            }
        }
        chessObjects.removeAll(objectsToRemove);
        return chessObjects;
    }
}
