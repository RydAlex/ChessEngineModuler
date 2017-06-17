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
        FullInsideGameDefiner amqp = new FullInsideGameDefiner();
        doLearningCallWithVotingMethod(amqp, TypeOfMessageExtraction.RANDOM);
        doLearningCallWithVotingMethod(amqp, TypeOfMessageExtraction.ELO);
        doLearningCallWithVotingMethod(amqp, TypeOfMessageExtraction.POSITION_WEIGHT);
        doLearningCallWithVotingMethod(amqp, TypeOfMessageExtraction.DEPTH_2);

    }

    private static void doLearningCallWithVotingMethod(FullInsideGameDefiner amqp, TypeOfMessageExtraction type) {
        //type choise - DEPTH
        IntStream.of(2,4,6,8).parallel().forEach(number_of_engine -> {
            amqp.createSimpleDepthGame(number_of_engine,3, type);
            amqp.createSimpleDepthGame(number_of_engine,5, type);
            amqp.createSimpleDepthGame(number_of_engine,7, type);
            amqp.createSimpleDepthGame(number_of_engine,9, type);
        });
        //type choise - Timeout
        if(!type.equals(TypeOfMessageExtraction.DEPTH_2)){
            IntStream.of(3000, 5000, 8000).parallel().forEach(timeout -> {
                amqp.createSimpleTimeoutGame(4,timeout, type);
                amqp.createSimpleTimeoutGame(4,timeout, type);
                amqp.createSimpleTimeoutGame(4,timeout, type);
            });
        }
    }


}
