package chess.geneticAlgorithm;

import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.database.service.EngineService;
import chess.utils.ChessCluster;
import chess.utils.name.spy.EngineSearcher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MutationMechanism {

    private static final int MAX_POPULATION_SIZE = 20;

    public static List<ChessCluster> mutate(List<ChessCluster> chessEnginesClusters) {
        LinkedList<ChessCluster> list = new LinkedList<>();
        for(ChessCluster cluster: chessEnginesClusters) {
            list.add(new ChessCluster(cluster.getCluster(), cluster.getEngineList()));
        }
        for(int i = list.size(); i<MAX_POPULATION_SIZE; i++){
            int whichClusterShouldBeMutate = new Random().nextInt(chessEnginesClusters.size());
            ChessCluster clusterToMutation = chessEnginesClusters.get(whichClusterShouldBeMutate);
            ChessCluster chessCluster = createNewClusterByMutation(clusterToMutation);
            while(list.contains(chessCluster)){
                chessCluster = createNewClusterByMutation(clusterToMutation);
            }
            list.add(chessCluster);
        }
        return list;
    }

    private static ChessCluster createNewClusterByMutation(ChessCluster clusterToMutation){
        ChessCluster chessCluster = new ChessCluster();

        Cluster cluster = new Cluster();
        cluster.setEloScore(1500);
        cluster.setEpochNumber(clusterToMutation.getCluster().getEpochNumber());
        chessCluster.setCluster(cluster);

        List<Engine> toMutationEngineList = new ArrayList<>(clusterToMutation.getEngineList());
        int howMuchMutationHere = new Random().nextInt(2)+1;
        for(int i=0; i<howMuchMutationHere ; i++){
            String newEngineName = EngineSearcher.getRandomChessEngineName();
            //Engine engine = new Engine(1, newEngineName);
            Engine engine = new EngineService().findByNameOrCreate(newEngineName);
            int whereMutate = new Random().nextInt(toMutationEngineList.size());
            toMutationEngineList.remove(whereMutate);
            toMutationEngineList.add(whereMutate, engine);
        }
        chessCluster.setEngineList(toMutationEngineList);
        return chessCluster;
    }
}