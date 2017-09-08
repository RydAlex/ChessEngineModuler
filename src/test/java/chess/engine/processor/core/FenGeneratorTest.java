package chess.engine.processor.core;

import chess.engine.processor.core.enginemechanism.FenGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FenGeneratorTest {
    private FenGenerator fenGen;
    String fenString;
    String[] fenStringDetails;

    @Before
    public void setUp() throws Exception {
        fenGen = new FenGenerator();
        fenString = fenGen.returnFenStringPositions();
        fenStringDetails = fenString.split(" ");
    }


    @Test
    public void initializeFen() {
        assertEquals(fenGen.returnChessboard().size(), 64);
    }

    @Test
    public void canReturnProperFormattedFenString() {
        assertEquals(fenString, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - -");
        System.out.println(fenString);
    }

    @Test
    public void canReturnProperWorkingSide() {
        assertEquals(fenStringDetails[1],"w");
    }

    //@Test
    public void canGenerateProperCastlingString() {
        assertEquals(fenStringDetails[2],"KQkq");
    }

    @Test
    public void canChangeChessboardByMove() {
        assertEquals(fenGen.returnFenStringPositions(), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - -");
        try {
            fenGen.insertMove("e2e4");
            fenGen.insertMove("c7c6");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(fenGen.returnFenStringPositions(), "rnbqkbnr/pp1ppppp/2p5/8/4P3/8/PPPP1PPP/RNBQKBNR w - -");
    }

    @Test
    public void isMoveExistForActivePlayer(){
        assertFalse(fenGen.isMoveExistForActivePlayer(null)); // null
        assertFalse(fenGen.isMoveExistForActivePlayer("a7a6")); // invalidMove cause other player
        assertFalse(fenGen.isMoveExistForActivePlayer("d4e4")); // invalidMove cause not exist
        assertTrue(fenGen.isMoveExistForActivePlayer("a2a4")); // validMove
    }


    @Test
    public void isCheckmateCanBeDetected(){
        assertTrue(fenGen.isMoveACheckmate(""));
        assertTrue(fenGen.isMoveACheckmate("a1a1"));
        assertFalse(fenGen.isMoveACheckmate("e1e2")); // king moves
        assertFalse(fenGen.isMoveACheckmate("e1e1")); // king castling
        assertFalse(fenGen.isMoveACheckmate("e8e8")); // king castling
        assertTrue(fenGen.isMoveACheckmate("(none)"));
        assertTrue(fenGen.isMoveACheckmate("0000"));
        assertFalse(fenGen.isMoveACheckmate("a1b2"));
    }

    @Test
    public void isCheckmateCanBeDetectedWhenFieldIsNull() {
        fenGen = new FenGenerator("k7/Q7/1K6/8/2P5/8/8/8 b - - 0 1");
        assertTrue(fenGen.isMoveACheckmate("a1a1"));
    }

    @Test
    public void canDetectPromotionInMove() {
        String fen = "8/K7/8/8/8/8/6p1/7k b - -";
        fenGen = new FenGenerator(fen);
        System.out.println(fenGen.returnFenStringPositions());
        try {
            fenGen.insertMove("g2g1q");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(fenGen.returnFenStringPositions());
    }

    @Test(expected = Exception.class)
    public void canReactWithExceptionOnWrongMove() throws Exception {
        String fen = "8/3Q4/3R1k2/8/5PK1/8/P7/8 b - -";
        fenGen = new FenGenerator(fen);
        System.out.println(fenGen.returnFenStringPositions());
        fenGen.insertMove("c3a3n");
    }

    @Test
    public void canDetectEnPessant() throws Exception {
        fenGen.insertMove("e2e4");
        assertEquals(fenGen.returnFenStringPositions(), "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b - e3");
        fenGen.insertMove("c7c6");
        assertEquals(fenGen.returnFenStringPositions(), "rnbqkbnr/pp1ppppp/2p5/8/4P3/8/PPPP1PPP/RNBQKBNR w - -");
    }

}