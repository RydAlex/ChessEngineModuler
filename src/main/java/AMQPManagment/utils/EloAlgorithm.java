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
        Long eloDelta = Math.round(getKFactor(myRating) * (result.getValue() - myChanceToWin));
        return eloDelta.intValue();
    }

    private static double getKFactor(Integer myRating) {
        if(myRating < 2100) return 32.0;
        else if(myRating >= 2100 && myRating < 2400) return 24;
        else return 16;
    }
}
