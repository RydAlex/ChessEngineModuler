package chess.utils.parsing.objects;

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
