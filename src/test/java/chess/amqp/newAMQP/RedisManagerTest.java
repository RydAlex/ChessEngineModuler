package chess.amqp.newAMQP;

import chess.redis.RedisAMQPManager;
import chess.redis.RedisManager;
import chess.utils.settings.Settings;
import org.junit.Test;

public class RedisManagerTest {
    @Test
    public void resetRedisResult(){
        while(RedisAMQPManager.getInformationAboutMessageInQueue(Settings.getChessProcessingQueueName()) >= 0 ){
            System.out.println(RedisAMQPManager.getInformationAboutMessageInQueue(Settings.getChessProcessingQueueName()));
            //RedisAMQPManager.reduceInformationAboutMessageInQueue(Settings.getChessProcessingQueueName());
        }
    }

    @Test
    public void showAllKeysInRedis(){
        RedisManager.printAllKeys();
    }
}