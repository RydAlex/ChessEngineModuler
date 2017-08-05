package chess.utils.json.object;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.EngineEloPair;

import java.util.List;

/**
 * Created by aleksanderr on 29/05/17.
 */
public class ChessJSONCreator {

    public static String createChessJsonWithTimeoutRule(String fenString, Integer timeout, TypeOfMessageExtraction typeOfGame,
                                                        boolean isSingleMove, List<String> chessGameNames, List<EngineEloPair> eloValues) {
        ChessJSONObject createdObj = createChessJsonObjects(fenString, null, timeout, typeOfGame, isSingleMove, chessGameNames, eloValues);
        return ChessJSONParser.createChessJson(createdObj);
    }

    public static String createChessJsonWithDepthRule(String fenString, Integer depth, TypeOfMessageExtraction typeOfGame,
                                                      boolean isSingleMove, List<String> chessGameNames, List<EngineEloPair> eloValues) {
        ChessJSONObject createdObj = createChessJsonObjects(fenString, depth, null, typeOfGame, isSingleMove, chessGameNames, eloValues);
        return ChessJSONParser.createChessJson(createdObj);
    }

    public static String createChessJsonFromObject(ChessJSONObject obj){
        return ChessJSONParser.createChessJson(obj);
    }

    private static ChessJSONObject createChessJsonObjects(String fenString, Integer depth, Integer timeout, TypeOfMessageExtraction typeOfGame,
                                                          boolean isSingleMove, List<String> chessGameNames, List<EngineEloPair> eloValues){
        ChessJSONObject obj = new ChessJSONObject();
        obj.setAnswer(null);
        obj.setTypeOfGame(typeOfGame);
        obj.setFen(fenString);
        obj.setChessGameName(chessGameNames);
        obj.setIsSingleMove(isSingleMove);
        obj.setChessGamesEloValue(eloValues);

        if(depth != null) obj.setDepth(depth);
        if(timeout != null) obj.setTimeout(timeout);
        return obj;
    }
}
