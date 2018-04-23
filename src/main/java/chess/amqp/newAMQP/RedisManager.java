package chess.amqp.newAMQP;

import redis.clients.jedis.Jedis;

public class RedisManager {

    protected static Jedis madeConnection() {
        Jedis jedis = null;
        while(jedis == null){
            try {
                jedis = new Jedis(System.getenv("REDIS_URL"));
            } catch(Exception e){
                System.out.println("i try to connect");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return jedis;
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
