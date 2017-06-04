package engineprocessor.core.enginemechanism;

import engineprocessor.core.utils.enums.ChessEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by aleksanderr on 10/07/16.
 */
public class FenGenerator {
    //"r1k4r/p2nb1p1/2b4p/1p1n1p2/2PP4/3Q1NB1/1P3PPP/R5K1 b - c3 0 19"
    //"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"        - Initial

    private SortedMap<String, String> chessBoard = new TreeMap<>();
    private boolean isWhiteActive = true;
    private String enPessant = "-";
    private ArrayList<String> castlingMove = new ArrayList<>(Arrays.asList("K","Q","k","q"));

    //White - uppercase
    //Black - lowercase
    //Numeric - pionowo
    //Literal - poziomo

    public FenGenerator(){
        for(Character literalSide = 'a' ; literalSide <= 'h' ; literalSide++ ){
            for(Integer numericalSide = 1 ; numericalSide <= 8 ; numericalSide++){
                String position = literalSide.toString()+numericalSide.toString();
                chessBoard.put(position,null);
            }
        }
        initializeMapWithStartValues();
    }

    public FenGenerator(String fen){
        String[] fenDetails = fen.split(" ");
        String board = fenDetails[0];
        setWhoseTurnIsNow(fenDetails[1].charAt(0));
        castlingMove.clear();
        for(String castlings : fenDetails[2].split("")){
            castlingMove.add(castlings);
        }
        enPessant = fenDetails[3];

        String[] fenLines = board.split("/");
        int lineNumber = 0;
        for(Integer numericalSide = 8 ; numericalSide >= 1 ; numericalSide--){
            String fenLine = fenLines[lineNumber];
            String[] fenSpots = fenLine.split("");
            int fenNumber = 0;
            for(Character literalSide = 'a' ; literalSide <= 'h' ; ){
                try{
                    int k = Integer.parseInt(fenSpots[fenNumber]);
                    for(int i=0 ; i<k ; i++){
                        String position = literalSide.toString()+numericalSide.toString();
                        chessBoard.put(position,null);
                        literalSide++ ;
                    }
                } catch(Exception ex){
                    String position = literalSide.toString()+numericalSide.toString();
                    chessBoard.put(position,fenSpots[fenNumber]);
                    literalSide++ ;
                }
                fenNumber++;
            }
            lineNumber++ ;
        }
    }

    public boolean insertMove(String move){
        try {
            String from = move.substring(0, 2);
            String to = move.substring(2, 4);
            String promotion = "";
            if (move.length() == 5) {
                promotion = move.substring(4, 5);
            }
            String figure = chessBoard.get(from);
            String figureTo = chessBoard.get(to);
            isMoveExistForActivePlayer(figure);
            isEnPessant(figure,from, to);
            chessBoard.put(to, figure);
            chessBoard.put(from, null);
            if (!promotion.trim().equals("")) {
                doPromotion(to, ChessEnum.fromString(promotion));
            }
            changeSideWhichHaveMove();
            return isHalfMoveBroken(figure, figureTo);
        } catch (Exception ex){
            throw new RuntimeException("Error while parsing move from engine --> " + move);
        }
    }

    private boolean isHalfMoveBroken(String figure, String figureTo) {
        // If Pone was moved than HalfMove should reset
        if(figure.equals("p") || figure.equals("P")){
            return true;
        }
        // if opponent figure was beaten also HalfMove should reset
        else if(figureTo != null) {
            return true;
        }
        return false;
    }

    private boolean isMoveExistForActivePlayer(String figure) {
        boolean uppercase = Character.isUpperCase(figure.codePointAt(0));
        if(uppercase && isWhiteActive){
            return true;
        } else if(!uppercase && !isWhiteActive){
            return true;
        }
        return false;
    }

    public boolean isMoveACheckmate(String move){
        if((move == null) ||
            move.equals("null") ||
            move.equals("NULL") ||
            move.equals("(none)") ||
            move.equals("")){
            return true;
        }
        String from = move.substring(0,2);
        String to = move.substring(2,4);
        if(from.equals(to)){
            if(chessBoard.get(from) == null){
                return true;
            }
            if(!from.equals("00") &&
                    (chessBoard.get(from).equals(ChessEnum.King.getBlackFigure()) ||
                    chessBoard.get(from).equals(ChessEnum.King.getWhiteFigure()))) {
               return false;
            }
            return true;
        } else {
            return false;
        }

    }

    private void isEnPessant(String figure,String from, String to) {
        String[] exactPositionFrom = from.split("");
        String[] exactPosition = to.split("");
        if(exactPositionFrom[1].equals("2") || exactPositionFrom[1].equals("7")) {
            if (figure.equals("p")) {
                if (exactPosition[1].equals("5")) {
                    enPessant = exactPosition[0] + "6";
                    return;
                }
            }

            if (figure.equals("P")) {
                if (exactPosition[1].equals("4")) {
                    enPessant = exactPosition[0] + "3";
                    return;
                }
            }
        }
        enPessant = "-";
    }


    public String returnFenStringPositions(){
        StringBuilder sb = new StringBuilder();

        for (Integer numericalSide = 8; numericalSide >= 1; numericalSide--){
            int counter=0;
            for(Character literalSide = 'a' ; literalSide <= 'h' ; literalSide++ ) {
                String cellValue = chessBoard.get(literalSide.toString()+numericalSide.toString());
                if(cellValue == null){
                    counter++;
                } else{
                    if(counter != 0) {
                        sb.append(counter);
                        counter = 0;
                    }
                    sb.append(cellValue);
                }

            }
            if(counter != 0){
                sb.append(counter);
            }
            if(numericalSide != 1){
                sb.append('/');
            }
        }

        sb.append(" ").append(whoseMoveIsNow())
                .append(" ").append(returnCastlingAllowance())
                .append(" ").append(enPessant);
        return sb.toString();
    }

    private void setWhoseTurnIsNow(char turnSymbolic){
        isWhiteActive = turnSymbolic == 'w';
    }
    private char whoseMoveIsNow(){
        return isWhiteActive ? 'w' : 'b';
    }

    private String returnCastlingAllowance(){
        StringBuilder sb = new StringBuilder();
        for(String castling : castlingMove){
            if(castling != null){
                sb.append(castling);
            }
        }
        String result = sb.toString();
        return "-"; //result.equals("") ? "-" : result;
    }

    //white 1-2 row -> big letters, black 7-8 row -> small letters
    private void initializeMapWithStartValues(){
        for(Character literalSide = 'a' ; literalSide<='h' ; literalSide++){
            chessBoard.put(literalSide.toString()+"2", ChessEnum.Pawn.getWhiteFigure());
            chessBoard.put(literalSide.toString()+"7", ChessEnum.Pawn.getBlackFigure());
            if(literalSide=='a' || literalSide=='h'){
                chessBoard.put(literalSide.toString()+"1", ChessEnum.Tower.getWhiteFigure());
                chessBoard.put(literalSide.toString()+"8", ChessEnum.Tower.getBlackFigure());
            }
            else if(literalSide=='b' || literalSide=='g'){
                chessBoard.put(literalSide.toString()+"1", ChessEnum.Knight.getWhiteFigure());
                chessBoard.put(literalSide.toString()+"8", ChessEnum.Knight.getBlackFigure());
            }
            else if(literalSide=='c' || literalSide=='f'){
                chessBoard.put(literalSide.toString()+"1", ChessEnum.Bishop.getWhiteFigure());
                chessBoard.put(literalSide.toString()+"8", ChessEnum.Bishop.getBlackFigure());
            }
            else if(literalSide=='d'){
                chessBoard.put(literalSide.toString()+"1", ChessEnum.Queen.getWhiteFigure());
                chessBoard.put(literalSide.toString()+"8", ChessEnum.Queen.getBlackFigure());
            }
            else if(literalSide=='e'){
                chessBoard.put(literalSide.toString()+"1", ChessEnum.King.getWhiteFigure());
                chessBoard.put(literalSide.toString()+"8", ChessEnum.King.getBlackFigure());
            }

        }
    }


    private void doPromotion(String onWhichField, ChessEnum onWhichFigure) {
        if(whoseMoveIsNow()=='w') {
            chessBoard.put(onWhichField,onWhichFigure.getWhiteFigure());
        } else {
            chessBoard.put(onWhichField, onWhichFigure.getBlackFigure());
        }
    }

    private void changeSideWhichHaveMove(){
        isWhiteActive = !isWhiteActive;
    }

    public SortedMap<String,String> returnChessboard(){
        return chessBoard;
    }

}
