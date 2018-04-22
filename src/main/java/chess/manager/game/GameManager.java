package chess.manager.game;

import chess.amqp.message.EngineEloPair;
import chess.amqp.message.TypeOfMessageExtraction;
import chess.amqp.newAMQP.AMQPReceiverImpl;
import chess.amqp.newAMQP.RedisManager;
import chess.database.entities.EngineName;
import chess.database.service.EngineNameService;
import chess.manager.game.definitions.FullInsideGameDefiner;
import chess.utils.settings.Settings;
import chess.utils.name.spy.EngineSearcher;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
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

        while(true){
            try {
                Integer messageCounter = RedisManager.getInformationAboutMessageInQueue(Settings.getChessProcessingQueueName());
                if (messageCounter < 300) {
                    while(RedisManager.getInformationAboutMessageInQueue(Settings.getChessProcessingQueueName()) < 500){
                        EngineSearcher.createPairsOfGames()
                                .stream()
                                .map(GameManager::findEloValueForEngineName)
                                .forEach(eloPair -> {
                                    gameDefiner.playFullActorTimeoutGameWithDefindedEnginesNamesAndDefinedSize(
                                            eloPair, 6000, TypeOfMessageExtraction.ELO_SIMPLE, 1);
                                });
                        log.info("I just have " + RedisManager.getInformationAboutMessageInQueue(Settings.getChessProcessingQueueName()) + " games to play in queue");
                        Thread.sleep(5000);
                    }
                } else {
                    log.info("There is too much messages. I need to wait. Due to Redis info there is a " + messageCounter + " messages.");
                    Thread.sleep(20000);
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<EngineEloPair> findEloValueForEngineName(List<String> engineNames) {
        EngineNameService engineNameService = new EngineNameService();
        List<EngineEloPair> engineEloPairs = new LinkedList<>();

        for(String engineName: engineNames){
            List<EngineName> engineNamesList = engineNameService.getEngineNameEntity(engineName);
            if(engineNamesList.isEmpty()){
                engineEloPairs.add(new EngineEloPair(engineName, 1500));
            } else {
                engineEloPairs.add(new EngineEloPair(engineName, engineNamesList.get(0).getCurrentElo()));
            }
        }
        return engineEloPairs;
    }

}
