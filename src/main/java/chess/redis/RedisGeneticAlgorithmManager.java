package chess.redis;

import chess.geneticAlgorithm.GeneticAlgorithmPhase;
import chess.utils.settings.Settings;
import redis.clients.jedis.Jedis;

public class RedisGeneticAlgorithmManager extends RedisManager {

    public static String GENETIC_ALGORITHM_ACTUAL_PHASE_VARIABLE = "GeneticPhase";
    public static String GENETIC_ALGORITHM_EPOCH_COUNTER = "GeneticEpochCounter";
    public static String JUMP_GUARD = "JumpGuard";

    public static GeneticAlgorithmPhase getActualPhase(){
        Jedis redisConn = madeConnection();
        GeneticAlgorithmPhase phase = GeneticAlgorithmPhase.fromString(redisConn.get(GENETIC_ALGORITHM_ACTUAL_PHASE_VARIABLE));
        if(phase == null){
            phase = GeneticAlgorithmPhase.INITIALIZATION;
            setPhase(phase);
        }
        redisConn.close();
        return phase;
    }

    public static void changePhase(){
        switch (getActualPhase()) {
            case INITIALIZATION:
                setPhase(GeneticAlgorithmPhase.EPOCH_EVALUATION);
                switchJumpGuardState();
                break;
            case EPOCH_EVALUATION:
                setPhase(GeneticAlgorithmPhase.SELECTION_CROSSOVER_MUTATION);
                break;
            case SELECTION_CROSSOVER_MUTATION:
                setPhase(GeneticAlgorithmPhase.EPOCH_EVALUATION);
                incrementEpochCounter();
                switchJumpGuardState();
                break;
            default:
                setPhase(GeneticAlgorithmPhase.INITIALIZATION);
                break;
        }
    }

    public static void switchJumpGuardState(){
        Jedis redisConn = madeConnection();
        if(!isJustJumpToEvaluationPhase()){
            redisConn.set(JUMP_GUARD, "True");
        } else {
            redisConn.del(JUMP_GUARD);
        }
        redisConn.close();
    }

    public static boolean isJustJumpToEvaluationPhase(){
        Jedis redisConn = madeConnection();
        boolean jumpGuard = redisConn.exists(JUMP_GUARD);
        redisConn.close();
        return jumpGuard;
    }

    public static int getEpochCounter(){
        Jedis redisConn = madeConnection();
        String counter = redisConn.get(GENETIC_ALGORITHM_EPOCH_COUNTER);
        if(counter == null){
            redisConn.set(GENETIC_ALGORITHM_EPOCH_COUNTER, "1");
            counter = "1";
        }
        redisConn.close();
        return Integer.parseInt(counter);
    }

    private static void incrementEpochCounter(){
        Jedis redisConn = madeConnection();
        redisConn.incr(GENETIC_ALGORITHM_EPOCH_COUNTER);
        redisConn.close();
    }

    private static void setPhase(GeneticAlgorithmPhase phase){
        Jedis redisConn = madeConnection();
        redisConn.set(GENETIC_ALGORITHM_ACTUAL_PHASE_VARIABLE, phase.getName());
        redisConn.close();
    }

    public void restartAllVariables(){
        Jedis redisConn = madeConnection();
        redisConn.del(Settings.getChessProcessingQueueName());
        redisConn.del(Settings.getChessSavingQueueName());
//        redisConn.del(GENETIC_ALGORITHM_EPOCH_COUNTER);
//        redisConn.del(GENETIC_ALGORITHM_ACTUAL_PHASE_VARIABLE);
//        redisConn.del(JUMP_GUARD);
        redisConn.close();
    }
}
