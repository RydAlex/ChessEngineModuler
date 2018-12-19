package chess.geneticAlgorithm;

import chess.database.entities.Cluster;
import chess.database.entities.Engine;
import chess.database.service.EngineService;
import chess.utils.ChessCluster;
import chess.utils.name.spy.EngineSearcher;
import lombok.extern.java.Log;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Log
public class CrossoverMechanism {


    public static List<ChessCluster> madeCrossover(List<ChessCluster> chessEnginesClusters) {
        LinkedList<ChessCluster> newChessClusters = new LinkedList<>();
        newChessClusters.addAll(chessEnginesClusters);
        createEnginesByRegularCrossover(chessEnginesClusters, newChessClusters);
        createEnginesBySpecialCrossover(chessEnginesClusters, newChessClusters);
        return newChessClusters;
    }

    private static void createEnginesBySpecialCrossover(List<ChessCluster> chessEnginesClusters, LinkedList<ChessCluster> newChessClusters) {
        final int AMOUNT_OF_SPECIALS_CROSSOVER_CLUSTERS = 5;
        ChessCluster newChessCluster;
        ChessCluster chessClusterOne = generateRandomChessEngine(chessEnginesClusters.get(0).getCluster().getEpochNumber());
        LinkedList<ChessCluster> specialCrossoveredClusters = new LinkedList<>();
        while(specialCrossoveredClusters.size() < AMOUNT_OF_SPECIALS_CROSSOVER_CLUSTERS){
            do {
                int secondEngineIndex = getSecondEngineIndex(chessEnginesClusters, -1);
                ChessCluster chessClusterTwo = chessEnginesClusters.get(secondEngineIndex);
                newChessCluster = crossOverConnection(chessClusterOne, chessClusterTwo);
            } while(notExistInThisPopulation(newChessClusters, specialCrossoveredClusters, newChessCluster));
            specialCrossoveredClusters.add(newChessCluster);
        }
        newChessClusters.addAll(specialCrossoveredClusters);
    }

    private static ChessCluster generateRandomChessEngine(int epoch) {
        LinkedList<Engine> engineList = new LinkedList<>();
        for(int i=0; i<30; i++) {
            String newEngineName = EngineSearcher.getRandomChessEngineName();
            engineList.add(new EngineService().findByNameOrCreate(newEngineName));
        }
        Cluster cluster = new Cluster(epoch,1500);
        return new ChessCluster(cluster, engineList);
    }

    private static void createEnginesByRegularCrossover(List<ChessCluster> chessEnginesClusters, LinkedList<ChessCluster> newChessClusters) {
        int counter = 0, counterLimit=10000;
        final int AMOUNT_OF_CROSSOVER_CLUSTERS = 5;
        ChessCluster newChessCluster;
        LinkedList<ChessCluster> crossoverClusters = new LinkedList<>();
        while(counter<counterLimit && crossoverClusters.size() < AMOUNT_OF_CROSSOVER_CLUSTERS){
            log.info("i enter into crossover generation");
            int firstEngineIndex = new Random().nextInt(chessEnginesClusters.size());
            ChessCluster chessClusterOne = chessEnginesClusters.get(firstEngineIndex);
            do {
                int secondEngineIndex = getSecondEngineIndex(chessEnginesClusters, firstEngineIndex);
                ChessCluster chessClusterTwo = chessEnginesClusters.get(secondEngineIndex);
                newChessCluster = crossOverConnection(chessClusterOne, chessClusterTwo);
                log.info("new cluster couldnt be found");
                counter++;
                if(counter>=counterLimit) return;
            } while(notExistInThisPopulation(chessEnginesClusters, crossoverClusters, newChessCluster));
            crossoverClusters.add(newChessCluster);
        }
        newChessClusters.addAll(crossoverClusters);
    }

    private static int getSecondEngineIndex(List<ChessCluster> chessEnginesClusters, int firstEngineIndex) {
        int secondEngineIndex;
        do{
            secondEngineIndex = new Random().nextInt(chessEnginesClusters.size());
            log.info("I choose " + secondEngineIndex + " when first engine index is " + firstEngineIndex);
        } while(firstEngineIndex == secondEngineIndex);
        return secondEngineIndex;
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
