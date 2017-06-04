package AMQPManagment.utils.chessJSONParsers;

import AMQPManagment.utils.data.ChessJSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

/**
 * Created by aleksanderr on 29/05/17.
 */
class ChessJSONParser {

    static String createChessJson(ChessJSONObject object){
        String json = null;
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(object);
            if(json == null) throw new RuntimeException();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    static ChessJSONObject parseChessJsonFromString(String jsonToParse){
        ChessJSONObject chessJSON = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            chessJSON = mapper.readValue(jsonToParse, ChessJSONObject.class);
            if(chessJSON == null) throw new RuntimeException();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return chessJSON;
    }
}
