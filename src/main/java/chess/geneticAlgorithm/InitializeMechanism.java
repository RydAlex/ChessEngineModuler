package chess.geneticAlgorithm;

import chess.database.service.ClusterService;
import chess.redis.RedisGeneticAlgorithmManager;
import chess.utils.ChessCluster;
import chess.utils.name.spy.EngineSearcher;

import java.util.List;

public class InitializeMechanism {
    public static List<ChessCluster> initializeGeneticGame() {
        ClusterService clusterService = new ClusterService();
        List<ChessCluster> chessClustersToReturn;

        if(RedisGeneticAlgorithmManager.getActualPhase().equals(GeneticAlgorithmPhase.INITIALIZATION)) {
            List<List<String>> chessEnginesClusters = EngineSearcher.createClusterOfChessEngines();
            chessClustersToReturn = clusterService.saveChessClustersToDatabase(chessEnginesClusters);
            RedisGeneticAlgorithmManager.changePhase();
        } else {
            chessClustersToReturn = clusterService.getCurrentClustersFromDatabase();
        }
        return chessClustersToReturn;
    }
}
