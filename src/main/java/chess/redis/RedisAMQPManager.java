package chess.redis;

import chess.utils.settings.Settings;
import redis.clients.jedis.Jedis;

public class RedisAMQPManager extends RedisManager{

    public static void increaseInformationAboutMessageInQueue(String queueName){
        Jedis jedis = madeConnection();
        jedis.incr(queueName);
        jedis.close();
    }

    public static void reduceInformationAboutMessageInQueue(String queueName){
        Jedis jedis = madeConnection();
        String amountInQueue = jedis.get(queueName);
        if(amountInQueue == null || Integer.parseInt(amountInQueue) == 0) {
            jedis.del(queueName);
        } else {
            jedis.decr(queueName);
        }
        jedis.close();
    }


    public static Integer getInformationAboutMessageInQueue(String queueName){
        Jedis jedis = madeConnection();
        String amountInQueue = jedis.get(queueName);
        if(amountInQueue == null) {
            amountInQueue = "0";
        }
        int numbersOfElementsInQueue = Integer.parseInt(amountInQueue);
        jedis.close();
        return numbersOfElementsInQueue;
    }

    public static void resetChessQueuesIndicators(){
        Jedis jedis = madeConnection();
        jedis.del(Settings.getChessProcessingQueueName());
        jedis.del(Settings.getChessSavingQueueName());
    }
}
