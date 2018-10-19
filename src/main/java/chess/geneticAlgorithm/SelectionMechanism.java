package chess.geneticAlgorithm;

import chess.database.entities.Cluster;
import chess.database.service.ClusterService;
import chess.utils.ChessCluster;

import java.util.LinkedList;
import java.util.List;

public class SelectionMechanism {
    public static List<ChessCluster> madeSelection() {
        LinkedList<ChessCluster> bestClustersFromDatabase = getBestClustersFromLastEpoch();
        return newEpochClusters(bestClustersFromDatabase);
    }

    private static List<ChessCluster> newEpochClusters(LinkedList<ChessCluster> bestClustersFromDatabase) {
        List<ChessCluster> newChessEnginesCluster = new LinkedList<>();
        for(ChessCluster chessCluster: bestClustersFromDatabase){
            ChessCluster newChessCluster = new ChessCluster();
            Cluster cluster = new Cluster();
            cluster.setEloScore(1500);
            cluster.setEpochNumber(chessCluster.getCluster().getEpochNumber() + 1);
            newChessCluster.setCluster(cluster);
            newChessCluster.setEngineList(chessCluster.getEngineList());
            newChessEnginesCluster.add(newChessCluster);
        }
        return newChessEnginesCluster;
    }

    private static LinkedList<ChessCluster> getBestClustersFromLastEpoch() {
        return new ClusterService().getBestClustersFromDatabase();
    }


}
