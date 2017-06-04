package AMQPManagment.utils.chessJSONParsers;

import AMQPManagment.utils.data.ChessJSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by aleksanderr on 03/06/17.
 */
public class ChessJSONTest {
    @Test
    public void createChessJsonWithTimeoutRuleWorksFine() throws Exception {
        LinkedList<String> engineList = new LinkedList<>();
        engineList.add("stockfish");
        engineList.add("fruit");
        List<String> listofDepthJson = ChessJSONCreator.createChessJsonWithTimeoutRule("adhd",5000,engineList);


        Assert.assertEquals("{\n  \"chessGameName\" : \"stockfish\",\n  \"timeout\" : 5000,\n  \"fen\" : \"adhd\"\n}",listofDepthJson.get(0));
        Assert.assertEquals("{\n  \"chessGameName\" : \"fruit\",\n  \"timeout\" : 5000,\n  \"fen\" : \"adhd\"\n}",listofDepthJson.get(1));
    }

    @Test
    public void createChessJsonWithDepthRule() throws Exception {
        LinkedList<String> engineList = new LinkedList<>();
        engineList.add("stockfish");
        engineList.add("fruit");
        List<String> listofDepthJson = ChessJSONCreator.createChessJsonWithDepthRule("adhd",5,engineList);

        Assert.assertEquals("{\n  \"chessGameName\" : \"stockfish\",\n  \"depth\" : 5,\n  \"fen\" : \"adhd\"\n}",listofDepthJson.get(0));
        Assert.assertEquals("{\n  \"chessGameName\" : \"fruit\",\n  \"depth\" : 5,\n  \"fen\" : \"adhd\"\n}",listofDepthJson.get(1));
    }

    @Test
    public void readChessJsonWithDepthRuleAndAnswer() throws Exception {
        ChessJSONObject jsonObject = ChessJSONReader.readDataFromJson("{\n  \"chessGameName\" : \"stockfish\",\n  \"depth\" : 5,\n  \"fen\" : \"adhd\",\n  \"answer\" : \"d5f3\"}");
        Assert.assertEquals("stockfish",jsonObject.getChessGameName());
        Assert.assertEquals(new Integer(5),jsonObject.getDepth());
        Assert.assertEquals("adhd",jsonObject.getFen());
        Assert.assertEquals("d5f3",jsonObject.getAnswer());

        Assert.assertEquals(null,jsonObject.getTimeout());
    }

    @Test
    public void readChessJsonWithTimeoutRule() throws Exception {
        ChessJSONObject jsonObject = ChessJSONReader.readDataFromJson("{\n  \"chessGameName\" : \"stockfish\",\n  \"timeout\" : 5000,\n  \"fen\" : \"adhd\",\n  \"answer\" : \"d5f3\"}");
        Assert.assertEquals("stockfish",jsonObject.getChessGameName());
        Assert.assertEquals(new Integer(5000),jsonObject.getTimeout());
        Assert.assertEquals("adhd",jsonObject.getFen());
        Assert.assertEquals("d5f3",jsonObject.getAnswer());

        Assert.assertEquals(null,jsonObject.getDepth());
    }

    @Test
    public void readMultipleChessJsonWithDepthRule() throws Exception {
        List<ChessJSONObject> listOfObj = ChessJSONReader.readDataFromJson(
                "{\n  \"chessGameName\" : \"stockfish\",\n  \"timeout\" : 5000,\n  \"fen\" : \"adhd\",\n  \"answer\" : \"d5f3\"}",
                "{\n  \"chessGameName\" : \"fruit\",\n  \"depth\" : 5,\n  \"fen\" : \"acdc\",\n  \"answer\" : \"d4a5\"}");

        Assert.assertEquals("stockfish",listOfObj.get(0).getChessGameName());
        Assert.assertEquals("fruit",listOfObj.get(1).getChessGameName());

        Assert.assertEquals(new Integer(5000),listOfObj.get(0).getTimeout());
        Assert.assertEquals(null,listOfObj.get(1).getTimeout());

        Assert.assertEquals(null,listOfObj.get(0).getDepth());
        Assert.assertEquals(new Integer(5),listOfObj.get(1).getDepth());

        Assert.assertEquals("adhd",listOfObj.get(0).getFen());
        Assert.assertEquals("acdc",listOfObj.get(1).getFen());

        Assert.assertEquals("d5f3",listOfObj.get(0).getAnswer());
        Assert.assertEquals("d4a5",listOfObj.get(1).getAnswer());
    }

}