package chess.manager.game.answer.processor;

import chess.algorithms.elo.EloAlgorithm;
import chess.algorithms.elo.EloGameResultValue;
import chess.amqp.message.ChessJSONObject;
import chess.database.dao.ClusterDAO;
import chess.database.entities.Cluster;

public class AnswerProcessor {

    public static synchronized void processAnswer(ChessJSONObject chessJSONObject) {
        String answer = chessJSONObject.getAnswer();
        if(!answer.equals("-1")){
            ClusterDAO clusterDao = new ClusterDAO();
            Cluster clusterOne = chessJSONObject.getClusterBattle().getChessClusterOne().getCluster();
            Cluster clusterTwo = chessJSONObject.getClusterBattle().getChessClusterTwo().getCluster();
            clusterOne = clusterDao.refresh(clusterOne, clusterOne.getId());
            clusterTwo = clusterDao.refresh(clusterTwo, clusterTwo.getId());

            if(clusterOne.getWhiteGames() < 50 && clusterTwo.getBlackGames() < 50){
                int newEloOne = EloAlgorithm.calculateRating(clusterOne.getEloScore(),
                        clusterTwo.getEloScore(), isWin(1, chessJSONObject));
                int newEloTwo = EloAlgorithm.calculateRating(clusterTwo.getEloScore(),
                        clusterOne.getEloScore(), isWin(2, chessJSONObject));

                clusterOne.setWhiteGames(clusterOne.getWhiteGames() + 1);
                clusterTwo.setBlackGames(clusterTwo.getBlackGames() + 1);

                clusterOne.setEloScore(newEloOne);
                clusterTwo.setEloScore(newEloTwo);

                clusterDao.edit(clusterOne);
                clusterDao.edit(clusterTwo);
            }
        }
    }

    private static EloGameResultValue isWin(int forWhichGroup, ChessJSONObject chessJSONObject) {
        if(chessJSONObject.takeAnswerAsInteger() == 0){
            return EloGameResultValue.DRAW;
        }
        if(chessJSONObject.takeAnswerAsInteger() == forWhichGroup){
            return EloGameResultValue.WIN;
        } else {
            return EloGameResultValue.LOSE;
        }
    }
}
