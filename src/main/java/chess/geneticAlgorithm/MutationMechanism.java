package chess.geneticAlgorithm;

import chess.database.dao.EngineDAO;
import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.database.service.EngineService;
import chess.utils.ChessCluster;
import chess.utils.name.spy.EngineSearcher;

import java.util.List;
import java.util.Random;

public class MutationMechanism {

    public static List<ChessCluster> mutate(List<ChessCluster> chessEnginesClusters) {
        for(int i=0; i<5; i++){
            int whichClusterShouldBeMutate = new Random().nextInt(chessEnginesClusters.size());
            ChessCluster clusterToMutation = chessEnginesClusters.get(whichClusterShouldBeMutate);
            ChessCluster chessCluster = createNewClusterByMutation(clusterToMutation);
            if(chessEnginesClusters.contains(chessCluster)){
                chessCluster = createNewClusterByMutation(clusterToMutation);
            }
            chessEnginesClusters.add(chessCluster);
        }
        return chessEnginesClusters;
    }

    public static ChessCluster createNewClusterByMutation(ChessCluster clusterToMutation){
        ChessCluster chessCluster = new ChessCluster();

        Cluster cluster = new Cluster();
        cluster.setEloScore(1500);
        cluster.setEpochNumber(clusterToMutation.getCluster().getEpochNumber());
        chessCluster.setCluster(cluster);

        List<Engine> toMutationEngineList = clusterToMutation.getEngineList();
        Engine engine = new EngineService().findByNameOrCreate(EngineSearcher.getRandomChessEngineName());
        int whereMutate = new Random().nextInt(toMutationEngineList.size());
        toMutationEngineList.remove(whereMutate);
        toMutationEngineList.add(whereMutate, engine);

        chessCluster.setEngineList(toMutationEngineList);
        return chessCluster;
    }
}