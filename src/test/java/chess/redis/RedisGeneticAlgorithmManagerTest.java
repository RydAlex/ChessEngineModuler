package chess.redis;

import chess.utils.settings.Settings;
import lombok.extern.java.Log;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static chess.redis.RedisGeneticAlgorithmManager.GENETIC_ALGORITHM_ACTUAL_PHASE_VARIABLE;
import static chess.redis.RedisGeneticAlgorithmManager.GENETIC_ALGORITHM_EPOCH_COUNTER;
import static chess.redis.RedisGeneticAlgorithmManager.JUMP_GUARD;
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
        log.info(redisManager.get(GENETIC_ALGORITHM_EPOCH_COUNTER));
        log.info(redisManager.get(GENETIC_ALGORITHM_ACTUAL_PHASE_VARIABLE));
        log.info(redisManager.get(JUMP_GUARD));
    }
}