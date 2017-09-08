package AMQPManagment.AMQPSender.dataFetchers;

import chess.amqp.message.TypeOfMessageExtraction;
import chess.amqp.message.ChessJSONObject;
import chess.amqp.message.EngineEloPair;
import chess.manager.messages.processors.EloProcessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        chessJSONObject.setDepth(15);
        chessJSONObject.setFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - -");
        chessJSONObject.setTypeOfGame(TypeOfMessageExtraction.ELO_SIMPLE);

        List<String> enginesNames = new LinkedList<>();
        enginesNames.add("blackmamba");
        enginesNames.add("ippolit");
        enginesNames.add("stockfish");
        enginesNames.add("gull");

        enginesNames.add("stockfish");
        enginesNames.add("komodo");
        enginesNames.add("ethereal");
        enginesNames.add("daydreamer");

        chessJSONObject.setChessGameName(enginesNames);

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
    }

    @Test
    public void testEloInsert(){
        EloProcessor eloProcessor = new EloProcessor();
        eloProcessor.fetchDataAndUpdateElo(chessJSONObject);
    }

    @Test
    public void testEngineNameEnginesAnswer(){
        List<String> enginesNames = new EloProcessor().createEngineNameEnginesAnswer(chessJSONObject);
        Assert.assertEquals(2,enginesNames.size());
        Assert.assertTrue(enginesNames.get(0).contains("_"));
        Assert.assertTrue(enginesNames.get(1).contains("_"));
    }

    @Test
    public void engineNamesAreReceivedOkayWith2EngineNames(){
        List<String> enginesNames = new LinkedList<>();
        enginesNames.add("blackmamba");
        enginesNames.add("ippolit");
        chessJSONObject.setChessGameName(enginesNames);

        List<String> testPackedReceived = new EloProcessor().createEngineNameEnginesAnswer(chessJSONObject);
        Assert.assertEquals(2,enginesNames.size());
        Assert.assertFalse(enginesNames.get(0).contains("_"));
        Assert.assertFalse(enginesNames.get(1).contains("_"));
    }


}