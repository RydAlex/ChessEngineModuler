package AMQPManagment.AMQPSender;

import AMQPManagment.utils.TypeOfMessageExtraction;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by aleksanderr on 04/06/17.
 */
@Slf4j
public class AMQPGameManager {

    public static void main(String[] argv){
        //Learning manager
        FullInsideGameDefiner gameDefiner = new FullInsideGameDefiner();
        doLearningCallWithVotingMethod(gameDefiner, TypeOfMessageExtraction.RANDOM);
//        doLearningCallWithVotingMethod(gameDefiner, TypeOfMessageExtraction.ELO);
//        doLearningCallWithVotingMethod(gameDefiner, TypeOfMessageExtraction.POSITION_WEIGHT);
//        doLearningCallWithVotingMethod(gameDefiner, TypeOfMessageExtraction.DEPTH_2);

//        SingleMoveGameDefiner singleMoveGameDefiner = new SingleMoveGameDefiner();
//        singleMoveGameDefiner.createSimpleDepthMoveRequest(8,7,TypeOfMessageExtraction.RANDOM);
    }

    private static void doLearningCallWithVotingMethod(FullInsideGameDefiner gameDefiner, TypeOfMessageExtraction type) {
        //type choise - DEPTH
        IntStream.of(2,4,6,8).parallel().forEach(number_of_engine -> {
            gameDefiner.createFullActorDepthGame(number_of_engine,3, type);
            gameDefiner.createFullActorDepthGame(number_of_engine,5, type);
            gameDefiner.createFullActorDepthGame(number_of_engine,7, type);
            gameDefiner.createFullActorDepthGame(number_of_engine,9, type);
        });
        //type choise - Timeout
        if(!type.equals(TypeOfMessageExtraction.DEPTH_2)){
            IntStream.of(3000, 5000, 8000).parallel().forEach(timeout -> {
                gameDefiner.createFullActorTimeoutGame(4,timeout, type);
                gameDefiner.createFullActorTimeoutGame(4,timeout, type);
                gameDefiner.createFullActorTimeoutGame(4,timeout, type);
            });
        }
    }


}
