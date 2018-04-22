package chess.amqp.newAMQP;

import chess.utils.settings.Settings;
import org.junit.Test;

public class RedisManagerTest {
    @Test
    public void resetRedisResult(){
        while(RedisManager.getInformationAboutMessageInQueue(Settings.getChessProcessingQueueName()) >= 0 ){
            System.out.println(RedisManager.getInformationAboutMessageInQueue(Settings.getChessProcessingQueueName()));
            RedisManager.reduceInformationAboutMessageInQueue(Settings.getChessProcessingQueueName());
        }
    }
}