package chess.manager.game.answer.processor;

import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.TypeOfMessageExtraction;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

public class AnswerProcessorTest{
    private static ChessJSONObject object;

    @BeforeClass
    public static void setUp(){
        object = new ChessJSONObject();
        object.setAnswer("1");
        object.setChessGameName(Arrays.asList("test1", "test2"));
        object.setSizeOfEnginesInFirstGroup(1);
        object.setFenMovesInGame("Hi hi hello");
        object.setFen("fen");
        object.setDepth(9);
        object.setTypeOfGame(TypeOfMessageExtraction.ELO_SIMPLE);
    }

    @Test
    public void testSaving(){
        AnswerProcessor.processAnswer(object);
        AnswerProcessor.processAnswer(object);
        AnswerProcessor.processAnswer(object);
        AnswerProcessor.processAnswer(object);

    }
}