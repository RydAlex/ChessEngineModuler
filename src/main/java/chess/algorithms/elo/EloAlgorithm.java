package chess.algorithms.elo;

/**
 * Created by aleksanderr on 09/07/17.
 */
public class EloAlgorithm {
    public static Integer calculateRating(Integer myRating, Integer opponentRating, EloGameResultValue result) {
        myRating += calculateEloDelta(myRating, opponentRating, result);
        if (myRating<0){
            return 0;
        }
        return myRating;
    }

    public static Integer calculateEloDelta(Integer myRating, Integer opponentRating, EloGameResultValue result){
        Double myChanceToWin = 1 / (1 + Math.pow(10, (opponentRating - myRating) / 400.0));
        Long eloDelta = Math.round(getKFactor(myRating) * (result.getValue() - myChanceToWin));
        return eloDelta.intValue();
    }

    private static double getKFactor(Integer myRating) {
        if(myRating < 2100) return 32.0;
        else if(myRating >= 2100 && myRating < 2400) return 24.0;
        else return 16.0;
    }

    public static Integer fetchOponentRatingFromDifference(int myOldRating, int myNewRating, EloGameResultValue result){
        double difference = myNewRating - myOldRating;

        double k = getKFactor(myOldRating);
        double myChanceToWin =  (k* result.getValue() - difference)/k ;

        if(myChanceToWin<0) {myChanceToWin = 0.0001;}
        if(myChanceToWin>1) {myChanceToWin = 0.9977;}

        double log = Math.log10(1/myChanceToWin - 1);
        Double resultToReturn = 400.0 * log + myOldRating;

        return (int)Math.round(resultToReturn);
    }
}
