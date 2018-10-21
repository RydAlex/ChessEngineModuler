package chess.redis;

import chess.utils.settings.Settings;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisManager {

    protected static Jedis madeConnection() {
        Jedis jedis = null;
        while (jedis == null) {
            try {
                jedis = new Jedis(Settings.getRedisURL());
            } catch (Exception e) {
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
