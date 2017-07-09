package AMQPManagment.utils;

import AMQPManagment.utils.data.GameResult;

/**
 * Created by aleksanderr on 09/07/17.
 */
public class EloAlgorithm {
    public static Integer calculateRating(Integer myRating, Integer opponentRating, GameResult result) {
        myRating += calculateEloDelta(myRating, opponentRating, result);
        return myRating;
    }

    private static Integer calculateEloDelta(Integer myRating, Integer opponentRating, GameResult result){
        Double myChanceToWin = 1 / (1 + Math.pow(10, (opponentRating - myRating) / 400));
        Long eloDelta = Math.round(32 * (result.getValue() - myChanceToWin));
        return eloDelta.intValue();
    }
}
