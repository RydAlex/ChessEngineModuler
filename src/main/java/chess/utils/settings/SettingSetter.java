package chess.utils.settings;

import chess.redis.RedisManager;
import redis.clients.jedis.Jedis;

public class SettingSetter extends RedisManager {
    public static void main(String[] argv){
        Jedis jedis = madeConnection();
        if(!System.getenv("CLOUDAMQP_URL").equals(jedis.get("CLOUDAMQP_URL"))){
            jedis.set("CLOUDAMQP_URL", System.getenv("CLOUDAMQP_URL"));
            System.out.println("CLOUDAMQP_URL changed on" + System.getenv("CLOUDAMQP_URL"));
        }
        if(!System.getenv("DATABASE_URL_DOMAIN").equals(jedis.get("DATABASE_URL_DOMAIN"))) {
            jedis.set("DATABASE_URL_DOMAIN", System.getenv("DATABASE_URL_DOMAIN"));
            System.out.println("DATABASE_URL_DOMAIN changed on" + System.getenv("DATABASE_URL_DOMAIN"));
        }
        if(!System.getenv("DATABASE_LOGIN").equals(jedis.get("DATABASE_LOGIN"))) {
            jedis.set("DATABASE_LOGIN", System.getenv("DATABASE_LOGIN"));
            System.out.println("DATABASE_LOGIN changed on" + System.getenv("DATABASE_LOGIN"));
        }
        if(!System.getenv("DATABASE_PASSWORD").equals(jedis.get("DATABASE_PASSWORD"))) {
            jedis.set("DATABASE_PASSWORD", System.getenv("DATABASE_PASSWORD"));
            System.out.println("DATABASE_PASSWORD changed on" + System.getenv("DATABASE_PASSWORD"));
        }
    }
}