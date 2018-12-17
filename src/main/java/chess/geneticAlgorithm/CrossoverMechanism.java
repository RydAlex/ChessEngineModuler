package chess.geneticAlgorithm;

import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.utils.ChessCluster;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Log
public class CrossoverMechanism {

    private static final int AMOUNT_OF_CROSSOVER_CLUSTERS = 15;

    public static List<ChessCluster> madeCrossover(List<ChessCluster> chessEnginesClusters) {
        LinkedList<ChessCluster> newChessClusters = new LinkedList<>();
        newChessClusters.addAll(chessEnginesClusters);
        while(newChessClusters.size() < AMOUNT_OF_CROSSOVER_CLUSTERS){
            log.info("i enter into crossover generation");
            int firstEngineIndex = new Random().nextInt(chessEnginesClusters.size());
            int secondEngineIndex = firstEngineIndex;
            while(firstEngineIndex == secondEngineIndex){
                secondEngineIndex = new Random().nextInt(chessEnginesClusters.size());
                log.info("i hit same index");
            }
            ChessCluster chessClusterOne = chessEnginesClusters.get(firstEngineIndex);
            ChessCluster chessClusterTwo = chessEnginesClusters.get(secondEngineIndex);
            ChessCluster newChessCluster = crossOverConnection(chessClusterOne, chessClusterTwo);
            while(notExistInThisPopulation(chessEnginesClusters, newChessClusters, newChessCluster)){
                newChessCluster = crossOverConnection(chessClusterOne, chessClusterTwo);
                log.info("new cluster couldnt be found");
            }
            newChessClusters.add(newChessCluster);
        }
        return newChessClusters;
    }

    private static boolean notExistInThisPopulation(List<ChessCluster> chessEnginesClusters,
                                                    LinkedList<ChessCluster> newChessClusters,
                                                    ChessCluster newChessCluster) {
        return chessEnginesClusters.contains(newChessCluster) || newChessClusters.contains(newChessCluster);
    }

    private static ChessCluster crossOverConnection(ChessCluster chessClusterOne, ChessCluster chessClusterTwo) {
        ChessCluster chessCluster = new ChessCluster();

        Cluster cluster = new Cluster();
        cluster.setEloScore(1500);
        cluster.setEpochNumber(chessClusterOne.getCluster().getEpochNumber());

        chessCluster.setCluster(cluster);

        int firstEngineIndex = new Random().nextInt(chessClusterTwo.getEngineList().size());
        int secondEngineIndex = firstEngineIndex;
        while(Math.abs(firstEngineIndex-secondEngineIndex) < 7){
            secondEngineIndex = new Random().nextInt(chessClusterTwo.getEngineList().size());
        }

        List<Engine> engineListOne = chessClusterOne.getEngineList();
        List<Engine> engineListTwo = chessClusterTwo.getEngineList();

        if(firstEngineIndex > secondEngineIndex){
            int temp = secondEngineIndex;
            secondEngineIndex = firstEngineIndex;
            firstEngineIndex = temp;
        }

        List<Engine> newEnginesForCluster1 = engineListOne.subList(0, firstEngineIndex);
        List<Engine> newEnginesForCluster2 = engineListTwo.subList(firstEngineIndex, secondEngineIndex);
        List<Engine> newEnginesForCluster3 = engineListOne.subList(secondEngineIndex, engineListOne.size());

        List<Engine> newEnginesForCluster = new LinkedList<>();
        newEnginesForCluster.addAll(new ArrayList<>(newEnginesForCluster1));
        newEnginesForCluster.addAll(new ArrayList<>(newEnginesForCluster2));
        newEnginesForCluster.addAll(new ArrayList<>(newEnginesForCluster3));
        chessCluster.setEngineList(newEnginesForCluster);

        return chessCluster;
    }
}
