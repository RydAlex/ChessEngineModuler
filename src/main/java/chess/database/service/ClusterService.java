package chess.database.service;

import chess.database.dao.ClusterDAO;
import chess.database.dao.EngineClusterListDAO;
import chess.database.dao.EngineDAO;
import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.database.entities.EngineClusterList;
import chess.utils.ChessCluster;

import java.util.LinkedList;
import java.util.List;

public class ClusterService {
    private EngineDAO engineDAO = new EngineDAO();
    private ClusterDAO clusterDAO = new ClusterDAO();
    private EngineClusterListDAO engineClusterListDAO = new EngineClusterListDAO();

    public List<ChessCluster> saveChessClustersToDatabase(List<List<String>> ListOfClustersOfEnginesNames) {
        List<ChessCluster> chessClusters = new LinkedList<>();
        for(List<String> clusterOfChessEngines : ListOfClustersOfEnginesNames){
            LinkedList<Engine> engineList = createListOfEngines_IfEngineDoesNotExistThanCreate(clusterOfChessEngines);
            Cluster cluster = createNewCluster();
            for(int engineSequenceNumber = 0; engineSequenceNumber < engineList.size(); engineSequenceNumber++){
                Engine engine = engineList.get(engineSequenceNumber);
                EngineClusterList engineClusterList = new EngineClusterList();
                engineClusterList.setSequenceNo(engineSequenceNumber);
                engineClusterList.setEngineByEngineId(engine.getId());
                engineClusterList.setClusterByClusterId(cluster.getId());
                engineClusterListDAO.edit(engineClusterList);
            }
            chessClusters.add(new ChessCluster(cluster,engineList));
        }
        return chessClusters;
    }

    private Cluster createNewCluster() {
        Cluster cluster = new Cluster();
        cluster.setEloScore(1500);
        cluster.setEpochNumber(1);
        clusterDAO.save(cluster);
        return cluster;
    }

    private LinkedList<Engine> createListOfEngines_IfEngineDoesNotExistThanCreate(List<String> clusterOfChessEngines) {

        LinkedList<Engine> parsedListOfEngines = new LinkedList<>();
        for(String chessEngineName : clusterOfChessEngines){
            Engine engine = engineDAO.getEngineByName(chessEngineName);
            if(engine == null) {
                engine = new Engine();
                engine.setEngineName(chessEngineName);
                engineDAO.save(engine);
            }
            parsedListOfEngines.add(engine);
        }
        return parsedListOfEngines;
    }

    public LinkedList<ChessCluster> getBestClustersFromDatabase() {
        ClusterDAO clusterDAO = new ClusterDAO();

        List<Cluster> clusters = clusterDAO.findTopClustersInCurrentEpoch(5);
        return findEnginesForClusterAndMadeChessClustersObj(clusters);
    }

    private LinkedList<ChessCluster> findEnginesForClusterAndMadeChessClustersObj(List<Cluster> clusters) {
        LinkedList<ChessCluster> currentClusters = new LinkedList<>();
        for(Cluster cluster: clusters){
            ChessCluster chessCluster = new ChessCluster();
            chessCluster.setCluster(cluster);
            chessCluster.setEngineList(engineDAO.findListOfEnginesForGivenCluster(cluster));
            currentClusters.add(chessCluster);
        }
        return currentClusters;
    }

    public LinkedList<ChessCluster> getCurrentClustersFromDatabase() {
        ClusterDAO clusterDAO = new ClusterDAO();

        List<Cluster> clusters = clusterDAO.findAllClustersInCurrentEpoch();
        return findEnginesForClusterAndMadeChessClustersObj(clusters);

    }
}
