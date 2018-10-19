package chess.utils.settings;

import chess.redis.RedisManager;
import redis.clients.jedis.Jedis;

public class SettingSetter extends RedisManager {
    public static void main(String[] argv){
        Jedis jedis = madeConnection();
        jedis.set("CLOUDAMQP_URL", System.getenv("CLOUDAMQP_URL"));
        jedis.set("DATABASE_URL_DOMAIN", System.getenv("DATABASE_URL_DOMAIN"));
        jedis.set("DATABASE_LOGIN", System.getenv("DATABASE_LOGIN"));
        jedis.set("DATABASE_PASSWORD", System.getenv("DATABASE_PASSWORD"));
    }
}