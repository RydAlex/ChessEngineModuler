package AMQPManagment.AMQPSender.dataFetchers;

import chess.amqp.message.ChessJSONObject;
import org.junit.Before;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksanderr on 09/07/17.
 */
public class EloProcessorTest {

    ChessJSONObject chessJSONObject = new ChessJSONObject();

    @Before
    public void prepareObject(){
        chessJSONObject.setAnswer("1");
        chessJSONObject.setFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - -");

        List<String> enginesNames = new LinkedList<>();
        enginesNames.add("blackmamba");
        enginesNames.add("ippolit");
        enginesNames.add("stockfish");
        enginesNames.add("gull3");

        enginesNames.add("stockfish");
        enginesNames.add("komodo");
        enginesNames.add("ethereal");
        enginesNames.add("daydreamer");
    }



}