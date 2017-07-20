package AMQPManagment.AMQPSender.dataFetchers;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.EngineEloPair;
import chess.manager.messages.processors.EloProcessor;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksanderr on 09/07/17.
 */
public class EloProcessorTest {

    @Test
    public void testEloInsert(){
        ChessJSONObject chessJSONObject = new ChessJSONObject();

        chessJSONObject.setAnswer("1");
        chessJSONObject.setDepth(15);
        chessJSONObject.setFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - -");
        chessJSONObject.setTypeOfGame(TypeOfMessageExtraction.ELO_SIMPLE);

        List<String> enginesNames = new LinkedList<>();
        enginesNames.add("blackmamba");
        enginesNames.add("ippolit");


        List<EngineEloPair> engineEloPairs = new LinkedList<>();
        EngineEloPair engineEloPair = new EngineEloPair();
        engineEloPair.setEngineName("blackmamba");
        engineEloPair.setEloValue(1000);
        engineEloPairs.add(engineEloPair);
        EngineEloPair engineEloPair2 = new EngineEloPair();
        engineEloPair2.setEngineName("ippolit");
        engineEloPair2.setEloValue(1000);
        engineEloPairs.add(engineEloPair2);
        chessJSONObject.setChessGamesEloValue(engineEloPairs);

        chessJSONObject.setIsSingleMove(false);

        EloProcessor eloProcessor = new EloProcessor();
        eloProcessor.fetchDataAndUpdateElo(chessJSONObject);
    }
}