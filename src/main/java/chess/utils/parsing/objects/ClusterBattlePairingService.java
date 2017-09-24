package chess.utils.parsing.objects;

import chess.amqp.message.EngineEloPair;
import chess.utils.name.spy.EngineSearcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by aleksanderr on 24/07/17.
 */
public class ClusterBattlePairingService {
    public static HashSet<List<EnginesCluster>> createPairs(){
        List<EnginesCluster> clusters = EngineSearcher.createPreDefinedClusters();
        HashSet<List<EnginesCluster>> clustersBattles = new HashSet<>();
        List<String> existingRules = Arrays.asList("depth_3", "depth_5", "depth_7", "depth_9",
                                                    "timeout_3000", "timeout_6000", "timeout_9000", "timeout_20000");



        for (String rule: existingRules) {
            List<EnginesCluster> currentCluster = new LinkedList<>();
            currentCluster.addAll(
                    clusters.stream()
                            .filter(cluster -> cluster.getPlayRule().equals(rule))
                            .collect(Collectors.toList())
            );
            clustersBattles.addAll(createBattlePairForThisLevel(currentCluster));
        }

        return clustersBattles;
    }

    public static LinkedList<List<EnginesCluster>> fetchEngineClusters() {
        List<EnginesCluster> clusters = EngineSearcher.createClustersToBattleVersusEnginesInIt();
        LinkedList<List<EnginesCluster>> clustersToReturn = new LinkedList<>();
        for(EnginesCluster enginesCluster : clusters){
            //for(EngineEloPair engine : enginesCluster.getEngineList()){
            //if(engine.getEngineName().contains("stockfish")){
            List<EnginesCluster> battlesList = new LinkedList<>();
            battlesList.add(enginesCluster);
            EnginesCluster enginesClusterNew = new EnginesCluster();
            enginesClusterNew.addEngineToCluster("stockfish");
            enginesClusterNew.setPlayRule(enginesCluster.getRuleValue());
            battlesList.add(enginesClusterNew);
            clustersToReturn.add(battlesList);
            //}
            //}
        }
        return clustersToReturn;
    }

    private static void createEngineVSEngineGames(LinkedList<List<EnginesCluster>> clustersToReturn, String name) {

        List<EnginesCluster> clusters = new LinkedList<>();
        EnginesCluster enginesCluster = new EnginesCluster();
        LinkedList<EngineEloPair> list = new LinkedList<>();
        list.add(new EngineEloPair(name, 0));
        enginesCluster.setEngineList(list);
        enginesCluster.setPlayRule(3000);
        clusters.add(enginesCluster);
        clusters.add(enginesCluster);
        clustersToReturn.add(clusters);
    }

    private static HashSet<List<EnginesCluster>> createBattlePairForThisLevel(List<EnginesCluster> currentClusters) {
        HashSet<List<EnginesCluster>> clustersPairs = new HashSet<>();

        String valuesAlreadyTaken = " ";
        Random random = new Random();
        for(int i=0 ; i<currentClusters.size()/2 ; i++){
            LinkedList<EnginesCluster> clusterPair = new LinkedList<>();
            int clusterOneNumber = -1;
            int clusterTwoNumber = -1;
            boolean clusterFreeFound = false;
            while(!clusterFreeFound) {
                clusterOneNumber = random.nextInt(currentClusters.size());
                if(!valuesAlreadyTaken.contains(" "+ clusterOneNumber + " ")){
                    clusterFreeFound = true;
                }
            }
            valuesAlreadyTaken += clusterOneNumber+" ";
            clusterFreeFound = false;
            while(!clusterFreeFound) {
                clusterTwoNumber = random.nextInt(currentClusters.size());
                if (!valuesAlreadyTaken.contains(" " + clusterTwoNumber + " ")) {
                    clusterFreeFound = true;
                }
            }
            valuesAlreadyTaken += clusterTwoNumber + " ";
            clusterPair.add(currentClusters.get(clusterOneNumber));
            clusterPair.add(currentClusters.get(clusterTwoNumber));
            clustersPairs.add(clusterPair);
        }
        return clustersPairs;
    }

}
