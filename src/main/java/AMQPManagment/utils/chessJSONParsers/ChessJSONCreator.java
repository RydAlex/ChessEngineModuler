package AMQPManagment.utils.chessJSONParsers;

import AMQPManagment.utils.data.ChessJSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksanderr on 29/05/17.
 */
public class ChessJSONCreator {

    public static List<String> createChessJsonWithTimeoutRule(String fenString, Integer timeout, List<String> chessGameNames) {
        List<ChessJSONObject> createdObj = createChessJsonObjects(fenString, null, timeout, chessGameNames);
        LinkedList<String> returnList = new LinkedList<>();
        for(ChessJSONObject obj : createdObj){
            returnList.add(ChessJSONParser.createChessJson(obj));
        }
        return returnList;
    }

    public static List<String> createChessJsonWithDepthRule(String fenString, Integer depth, List<String> chessGameNames) {
        List<ChessJSONObject> createdObj = createChessJsonObjects(fenString, depth, null, chessGameNames);
        LinkedList<String> returnList = new LinkedList<>();
        for(ChessJSONObject obj : createdObj){
            returnList.add(ChessJSONParser.createChessJson(obj));
        }
        return returnList;
    }

    public static String createChessJsonFromObject(ChessJSONObject obj){
        return ChessJSONParser.createChessJson(obj);
    }

    private static List<ChessJSONObject> createChessJsonObjects(String fenString, Integer depth, Integer timeout, List<String> chessGameNames){
        LinkedList<ChessJSONObject> objects = new LinkedList<>();
        for(String element : chessGameNames){
            ChessJSONObject obj = new ChessJSONObject();
            obj.setAnswer(null);
            obj.setFen(fenString);
            obj.setChessGameName(element);

            if(depth != null) obj.setDepth(depth);
            if(timeout != null) obj.setTimeout(timeout);
            objects.add(obj);
        }
        return objects;
    }



}
