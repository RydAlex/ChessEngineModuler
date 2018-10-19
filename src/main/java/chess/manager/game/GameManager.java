package chess.manager.game;

import chess.amqp.newAMQP.AMQPReceiverImpl;
import chess.geneticAlgorithm.*;
import chess.manager.game.definitions.FullInsideGameDefiner;
import chess.redis.RedisGeneticAlgorithmManager;
import chess.utils.ChessCluster;
import chess.utils.settings.Settings;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by aleksanderr on 04/06/17.
 */
@Slf4j
public class GameManager {

    public static void main(String[] argv){
        //Learning manager
        FullInsideGameDefiner gameDefiner = new FullInsideGameDefiner();

        // Run Receiver
        new Thread(() -> {
            AMQPReceiverImpl receiver = new AMQPReceiverImpl();
            receiver.recvFromQueue(Settings.getChessSavingQueueName());
        }).start();

        List<ChessCluster> chessEnginesClusters = InitializeMechanism.initializeGeneticGame();
        while(true){
            EvaluationMechanism.evaluate(chessEnginesClusters);
            chessEnginesClusters = madeNewGeneration(chessEnginesClusters);
        }
    }

    private static List<ChessCluster> madeNewGeneration(List<ChessCluster> chessEnginesClusters) {
        if(RedisGeneticAlgorithmManager.getActualPhase().equals(GeneticAlgorithmPhase.SELECTION_CROSSOVER_MUTATION)) {
            chessEnginesClusters = SelectionMechanism.madeSelection();
            chessEnginesClusters = CrossoverMechanism.madeCrossover(chessEnginesClusters);
            chessEnginesClusters = MutationMechanism.mutate(chessEnginesClusters);
            SaveNewPopulationMechanism.saveNewPopulation(chessEnginesClusters);
            RedisGeneticAlgorithmManager.changePhase();
        }
        return chessEnginesClusters;
    }



}