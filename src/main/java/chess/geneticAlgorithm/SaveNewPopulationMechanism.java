package chess.geneticAlgorithm;

import chess.database.dao.ClusterDAO;
import chess.database.dao.EngineClusterListDAO;
import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.database.entities.EngineClusterList;
import chess.utils.ChessCluster;

import java.util.List;

public class SaveNewPopulationMechanism {

    public static void saveNewPopulation(List<ChessCluster> chessEnginesClusters) {
        EngineClusterListDAO engineClusterListDAO = new EngineClusterListDAO();
        ClusterDAO clusterDAO = new ClusterDAO();

        for(ChessCluster chessCluster: chessEnginesClusters){
            Cluster cluster = chessCluster.getCluster();
            clusterDAO.save(cluster);
            List<Engine> engineList = chessCluster.getEngineList();
            for(int i=0; i<engineList.size(); i++){
                EngineClusterList engineClusterList = new EngineClusterList();
                engineClusterList.setSequenceNo(i);
                engineClusterList.setClusterByClusterId(cluster.getId());
                engineClusterList.setEngineByEngineId(engineList.get(i).getId());
                engineClusterListDAO.save(engineClusterList);
            }
        }
    }
}
