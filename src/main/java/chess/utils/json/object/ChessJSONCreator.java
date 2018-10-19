package chess.utils.json.object;

import chess.amqp.message.ChessJSONObject;
import chess.utils.ChessClusterBattle;

/**
 * Created by aleksanderr on 29/05/17.
 */
public class ChessJSONCreator {

    public static String createChessJsonWithTimeoutRule(String fenString, Integer timeout,
                                                        ChessClusterBattle clusterBattle) {
        ChessJSONObject obj = new ChessJSONObject();
        obj.setAnswer(null);
        obj.setFen(fenString);
        obj.setTimeout(timeout);
        obj.setClusterBattle(clusterBattle);
        return ChessJSONParser.createChessJson(obj);
    }


    public static String createChessJsonFromObject(ChessJSONObject obj){
        return ChessJSONParser.createChessJson(obj);
    }
}
