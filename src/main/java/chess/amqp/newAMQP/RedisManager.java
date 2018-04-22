package chess.amqp.newAMQP;

import redis.clients.jedis.Jedis;

public class RedisManager {

    protected static Jedis madeConnection(){
        return new Jedis(System.getenv("REDIS_URL"));
    }

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
}
