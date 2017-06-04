package AMQPManagment.utils.chessJSONParsers;

import AMQPManagment.utils.data.ChessJSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksanderr on 29/05/17.
 */
public class ChessJSONReader {
    public static ChessJSONObject readDataFromJson(String json){
        return ChessJSONParser.parseChessJsonFromString(json);
    }

    public static List<ChessJSONObject> readDataFromJson(String... jsons){
        LinkedList<ChessJSONObject> listToReturn = new LinkedList<>();
        for(String json : jsons){
            listToReturn.add(ChessJSONParser.parseChessJsonFromString(json));
        }
        return listToReturn;
    }
}
