package engineprocessor.core;

import engineprocessor.core.enginemechanism.FenGenerator;
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
        assertEquals(fenString, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -");
        System.out.println(fenString);
    }

    @Test
    public void canReturnProperWorkingSide() {
        assertEquals(fenStringDetails[1],"w");
    }

    @Test
    public void canGenerateProperCastlingString() {
        assertEquals(fenStringDetails[2],"KQkq");
    }

    @Test
    public void canChangeChessboardByMove() {
        assertEquals(fenGen.returnFenStringPositions(), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -");
        fenGen.insertMove("e2e4")
                .insertMove("c7c6");
        assertEquals(fenGen.returnFenStringPositions(), "rnbqkbnr/pp1ppppp/2p5/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq -");
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
    public void canDetectPromotionInMove() {
        String fen = "8/K7/8/8/8/8/6p1/7k b - -";
        fenGen = new FenGenerator(fen);
        System.out.println(fenGen.returnFenStringPositions());
        fenGen.insertMove("g2g1q");
        System.out.println(fenGen.returnFenStringPositions());
    }

    @Test
    public void canDetectEnPessant() {
        fenGen.insertMove("e2e4");
        assertEquals(fenGen.returnFenStringPositions(), "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3");
        fenGen.insertMove("c7c6");
        assertEquals(fenGen.returnFenStringPositions(), "rnbqkbnr/pp1ppppp/2p5/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq -");
    }

}