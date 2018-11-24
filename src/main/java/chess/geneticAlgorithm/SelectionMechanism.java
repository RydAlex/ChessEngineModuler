package chess.geneticAlgorithm;

import chess.database.dao.ClusterDAO;
import chess.database.dao.EngineDAO;
import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.database.service.ClusterService;
import chess.utils.ChessCluster;

import java.util.LinkedList;
import java.util.List;

public class SelectionMechanism {
    public static List<ChessCluster> madeSelection() {
        LinkedList<ChessCluster> bestClustersFromDatabase = getBestClustersFromLastEpoch();
        bestClustersFromDatabase = addClearBestCluster(bestClustersFromDatabase);
        return newEpochClusters(bestClustersFromDatabase);
    }

    private static LinkedList<ChessCluster> addClearBestCluster(LinkedList<ChessCluster> bestClustersFromDatabase) {
        List<Engine> engines = new LinkedList<>();
        for(int i=0 ; i<engines.size(); i++) {
            engines.add(new EngineDAO().getEngineByName("stockfish9"));
        }
        Cluster cluster = new Cluster(bestClustersFromDatabase.get(0).getCluster().getEpochNumber(), 1500);
        new ClusterDAO().save(cluster);
        bestClustersFromDatabase.add(new ChessCluster(cluster, engines));
        return bestClustersFromDatabase;
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
