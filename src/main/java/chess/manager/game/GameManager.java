package chess.manager.game;

import chess.amqp.message.EngineEloPair;
import chess.manager.game.definitions.FullInsideGameDefiner;
import chess.manager.data.parsers.EngineEloPairParser;
import chess.utils.name.spy.EngineSearcher;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.utils.parsing.objects.ClusterBattlePairingService;
import chess.utils.parsing.objects.EnginesCluster;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;

/**
 * Created by aleksanderr on 04/06/17.
 */
@Slf4j
public class GameManager {

    public static void main(String[] argv){
        //Learning manager
        FullInsideGameDefiner gameDefiner = new FullInsideGameDefiner();
        while(true){
            doLearningCallWithEloTypeAndFightOneVsOne(gameDefiner);
//            doLearningCallForClusters(gameDefiner);
        }
    }

    private static void doLearningCallForClusters(FullInsideGameDefiner gameDefiner) {

        //Map - List of chess engines names, play_rule
        HashSet<List<EnginesCluster>> enginesClustersBattles = ClusterBattlePairingService.createPairs();
        enginesClustersBattles.stream()
                .parallel()
                .map(EngineEloPairParser::findElosForEnginesInClusters)
                .forEach(enginesClusters -> {
                    EnginesCluster cluster = enginesClusters.get(0);
                    log.info("Play Rule: " + cluster.getPlayRule());
                    for(EngineEloPair pair : cluster.getEngineList()){
                        log.info("Engine Name: " + pair.getEngineName() + " Elo Value: " + pair.getEloValue());
                    }
                    cluster = enginesClusters.get(1);
                    log.info("Play Rule: " + cluster.getPlayRule());
                    for(EngineEloPair pair : cluster.getEngineList()){
                        log.info("Engine Name: " + pair.getEngineName() + " Elo Value: " + pair.getEloValue());
                    }
                    if(cluster.getRuleType().equals("timeout")){
                        gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(cluster.getEngineList(), cluster.getRuleValue(), TypeOfMessageExtraction.ELO_SIMPLE);
//                        gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(cluster.getEngineList(), cluster.getRuleValue(), TypeOfMessageExtraction.ELO_VOTE_WITH_DISTRIBUTION);
//                        gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(cluster.getEngineList(), cluster.getRuleValue(), TypeOfMessageExtraction.ELO_VOTE_WITH_ELO);
//                        gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(cluster.getEngineList(), cluster.getRuleValue(), TypeOfMessageExtraction.RANDOM);
                    } else if(cluster.getRuleType().equals("depth")){
                        gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(cluster.getEngineList(), cluster.getRuleValue(), TypeOfMessageExtraction.ELO_SIMPLE);
//                        gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(cluster.getEngineList(), cluster.getRuleValue(), TypeOfMessageExtraction.ELO_VOTE_WITH_DISTRIBUTION);
//                        gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(cluster.getEngineList(), cluster.getRuleValue(), TypeOfMessageExtraction.ELO_VOTE_WITH_ELO);
//                        gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(cluster.getEngineList(), cluster.getRuleValue(), TypeOfMessageExtraction.RANDOM);
                    }

                });
    }

    private static void doLearningCallWithEloTypeAndFightOneVsOne(FullInsideGameDefiner gameDefiner) {
        //type choise - DEPTH
        //List<List<String>> listOfEnginesGames = EngineSearcher.createPairsOfGames();
        List<List<String>> listOfEnginesGames = EngineSearcher.rematchBestEnginesForDepthOrTimeout();
        listOfEnginesGames.stream()
                .parallel()
                .map(EngineEloPairParser::findElosForEngineNamesAndCreateEngineEloPair)
                .forEach(engineEloPair -> {
//                    // Elo simple games from longest to shortest
                    gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(engineEloPair, 20000, TypeOfMessageExtraction.ELO_SIMPLE);
                    gameDefiner.playFullActorDepthGameWithDefinedEnginesNames(engineEloPair, 9, TypeOfMessageExtraction.ELO_SIMPLE);

                    gameDefiner.playFullActorDepthGameWithDefinedEnginesNames(engineEloPair, 7, TypeOfMessageExtraction.ELO_SIMPLE);
                    gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(engineEloPair, 9000, TypeOfMessageExtraction.ELO_SIMPLE);

                    gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(engineEloPair, 6000, TypeOfMessageExtraction.ELO_SIMPLE);
                    gameDefiner.playFullActorDepthGameWithDefinedEnginesNames(engineEloPair, 5, TypeOfMessageExtraction.ELO_SIMPLE);

                    gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNames(engineEloPair, 3000, TypeOfMessageExtraction.ELO_SIMPLE);
                    gameDefiner.playFullActorDepthGameWithDefinedEnginesNames(engineEloPair, 3, TypeOfMessageExtraction.ELO_SIMPLE);

                });
    }

}
