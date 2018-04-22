package chess.utils.settings;

import chess.amqp.newAMQP.RedisManager;
import redis.clients.jedis.Jedis;

public class Settings extends RedisManager{

    public static String getChessProcessingQueueName(){
        return "ChessGameProcessingQueue";
    }

    public static String getChessSavingQueueName(){
        return "SaveChessGameQueue";
    }

    public static String getAMQPString(){
        Jedis jedis = madeConnection();
        String amqpURL = jedis.get("CLOUDAMQP_URL");
        jedis.close();
        return amqpURL;
    }

    public static String getPostgresDBUrl(){
        //Remember URL must contain "jdbc:postgresql:..." not "postgres://" - SQL on the end is important
        Jedis jedis = madeConnection();
        String dbURL = jedis.get("DATABASE_URL_DOMAIN");
        jedis.close();
        return dbURL;
    }

    public static String getPostgresDBUsername(){
        Jedis jedis = madeConnection();
        String dbUsername = jedis.get("DATABASE_LOGIN");
        jedis.close();
        return dbUsername;
    }

    public static String getPostgresDBPassword(){
        Jedis jedis = madeConnection();
        String dbPassword = jedis.get("DATABASE_PASSWORD");
        jedis.close();
        return dbPassword;
    }
}
