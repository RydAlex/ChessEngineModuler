package chess.redis;

import chess.utils.settings.Settings;
import lombok.extern.java.Log;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

@Log
public class RedisGeneticAlgorithmManagerTest {
    @Test
    public void resetRedisVariableConnectedWithGeneticAlg(){
        RedisGeneticAlgorithmManager redisMan = new RedisGeneticAlgorithmManager();
        redisMan.restartAllVariables();
    }

    @Test
    public void getQueueInfo(){
        Jedis redisManager = RedisManager.madeConnection();
        log.info(redisManager.get(Settings.getChessProcessingQueueName()));
        log.info(redisManager.get(Settings.getChessSavingQueueName()));
    }
}