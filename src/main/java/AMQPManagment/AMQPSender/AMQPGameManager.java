package AMQPManagment.AMQPSender;

import AMQPManagment.AMQPSender.gameDefinitions.FullInsideGameDefiner;
import AMQPManagment.utils.EngineEloPairParser;
import AMQPManagment.utils.EngineSearcher;
import AMQPManagment.utils.TypeOfMessageExtraction;
import AMQPManagment.utils.data.EngineEloPair;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aleksanderr on 04/06/17.
 */
@Slf4j
public class AMQPGameManager {

    public static void main(String[] argv){
        //Learning manager
        FullInsideGameDefiner gameDefiner = new FullInsideGameDefiner();
        doLearningCallWithEloTypeAndFightOneVsOne(gameDefiner);

//      SingleMoveGameDefiner singleMoveGameDefiner = new SingleMoveGameDefiner();
//      SingleMoveGameDefiner.createSimpleDepthMoveRequest(8,7,TypeOfMessageExtraction.RANDOM);
    }

    private static void doLearningCallWithEloTypeAndFightOneVsOne(FullInsideGameDefiner gameDefiner) {
        //type choise - DEPTH
        List<List<String>> listOfEnginesGames = EngineSearcher.createPairsOfGames();
        listOfEnginesGames.stream()
                .parallel()
                .map(EngineEloPairParser::findElosForEngineNamesAndCreateEngineEloPair)
                .forEach(engineEloPair -> {
                    gameDefiner.playFullActorDepthGameWithDefinedEnginesNames(engineEloPair,3, TypeOfMessageExtraction.ELO_SIMPLE);
                    gameDefiner.playFullActorDepthGameWithDefinedEnginesNames(engineEloPair,5, TypeOfMessageExtraction.ELO_SIMPLE);
                    gameDefiner.playFullActorDepthGameWithDefinedEnginesNames(engineEloPair,7, TypeOfMessageExtraction.ELO_SIMPLE);
                    gameDefiner.playFullActorDepthGameWithDefinedEnginesNames(engineEloPair,9, TypeOfMessageExtraction.ELO_SIMPLE);
                    gameDefiner.playFullActorDepthGameWithDefinedEnginesNames(engineEloPair,15, TypeOfMessageExtraction.ELO_SIMPLE);
        });
    }


}
