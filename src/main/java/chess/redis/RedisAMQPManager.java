package chess.redis;

import chess.utils.settings.Settings;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class RedisAMQPManager extends RedisManager{

    public static void increaseInformationAboutMessageInQueue(String queueName){
        Jedis jedis = madeConnection();
        jedis.incr(queueName);
        jedis.close();
    }

    public static void reduceInformationAboutMessageInQueue(String queueName, Channel channel, Envelope envelope) throws IOException {
        Jedis jedis = madeConnection();

        //shouldnt be here - for test purpose.
        channel.basicAck(envelope.getDeliveryTag(), false);

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
