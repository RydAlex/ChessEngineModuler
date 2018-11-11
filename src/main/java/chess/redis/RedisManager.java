package chess.redis;

import chess.utils.settings.Settings;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisManager {

    protected static Jedis madeConnection() {
        Jedis jedis = null;
        int connectionTry = 0;
        while (jedis == null) {
            try {
                connectionTry += 1;
                jedis = new Jedis(Settings.getRedisURL());
            } catch (Exception e) {
                if(connectionTry >= 300) //5 min problem
                    throw new RuntimeException("Problem with connection to Redis");
                System.out.println("i try to connect to Redis");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return jedis;
    }


    public static void printAllKeys() {
        Jedis redisConn = madeConnection();
        Set<String> keys = redisConn.keys("*");
        for(String key: keys){
            System.out.println(key + " -> " + redisConn.get(key));
        }
    }

}
