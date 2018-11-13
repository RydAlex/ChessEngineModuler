package chess.geneticAlgorithm;

import chess.manager.game.definitions.FullInsideGameDefiner;
import chess.redis.RedisAMQPManager;
import chess.redis.RedisGeneticAlgorithmManager;
import chess.utils.ChessCluster;
import chess.utils.ChessClusterBattle;
import chess.utils.settings.Settings;
import lombok.extern.java.Log;

import java.util.LinkedList;
import java.util.List;

@Log
public class EvaluationMechanism {


    public static void evaluate(List<ChessCluster> chessEnginesClusters) {
        if(RedisGeneticAlgorithmManager.getActualPhase().equals(GeneticAlgorithmPhase.EPOCH_EVALUATION)) {
            if(RedisGeneticAlgorithmManager.isJustJumpToEvaluationPhase()){
                sendChessEnginesToAMQP(chessEnginesClusters);
                log.info("Jump Guard turned off");
                RedisGeneticAlgorithmManager.switchJumpGuardState();
            }
            log.info("I send all games to queue. Now i will wait");
            waitUntilAllGameWasPlayed();
            RedisGeneticAlgorithmManager.changePhase();
        }
    }

    private static void waitUntilAllGameWasPlayed() {
        int counter=0;
        int savingAmount = 0;
        int sendingAmount = 0;
        while(getSendingQueueMessagesAmount() > 0 || getSavingQueueMessagesAmount() > 0) {
            try {
                if(getSendingQueueMessagesAmount() < 20 || getSavingQueueMessagesAmount() < 20){
                    if(sendingAmount == getSendingQueueMessagesAmount() && savingAmount == getSavingQueueMessagesAmount()){
                        counter++;
                        if(counter>1000){ // not whole 3 hours
                            RedisAMQPManager.resetChessQueuesIndicators();
                            log.info("BROKE WAIT");
                            break;
                        }
                    } else {
                        savingAmount = getSavingQueueMessagesAmount();
                        sendingAmount = getSendingQueueMessagesAmount();
                        counter = 0;
                    }
                }
                log.info("I still wait on end!");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static int getSendingQueueMessagesAmount(){
        return RedisAMQPManager.getInformationAboutMessageInQueue(Settings.getChessProcessingQueueName());
    }

    private static int getSavingQueueMessagesAmount(){
        return RedisAMQPManager.getInformationAboutMessageInQueue(Settings.getChessProcessingQueueName());
    }

    private static void sendChessEnginesToAMQP(List<ChessCluster> chessEnginesClusters) {
        createChessClusterBattles(chessEnginesClusters)
                .forEach(clusterBattle -> FullInsideGameDefiner.playGameWithThisCluster(6000, clusterBattle));
    }


    protected static List<ChessClusterBattle> createChessClusterBattles(List<ChessCluster> chessEnginesClusters) {
        // Send 76 games by each cluster - 38 created by engine and 38 created by other engines with this one
        List<ChessClusterBattle> battles = new LinkedList<>();
        for(int firstEngineIndex = 0; firstEngineIndex < chessEnginesClusters.size(); firstEngineIndex++){
            for(int i=0; i<2; i++){
                for(int secondEngineIndex = 0; secondEngineIndex < chessEnginesClusters.size(); secondEngineIndex++){
                    if(secondEngineIndex != firstEngineIndex){
                        ChessClusterBattle chessClusterBattle = new ChessClusterBattle();
                        chessClusterBattle.setChessClusterOne(chessEnginesClusters.get(firstEngineIndex));
                        chessClusterBattle.setChessClusterTwo(chessEnginesClusters.get(secondEngineIndex));
                        battles.add(chessClusterBattle);
                    }
                }
            }
        }
        return battles;
    }
}
