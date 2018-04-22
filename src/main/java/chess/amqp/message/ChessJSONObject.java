package chess.amqp.message;

import chess.algorithms.elo.EloGameResultValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "chessGameName",
        "depth",
        "timeout",
        "fen",
        "typeOfGame",
        "answer",
        "chessGamesEloValue",
        "engineNamesVotesMap"
})
public class ChessJSONObject {

    @JsonProperty("chessGameName")
    private List<String> chessGameName;
    @JsonProperty("chessGamesEloValue")
    private List<EngineEloPair> chessGamesEloValue;
    @JsonProperty("engineNamesVotesMap")
    private List<GameVotingStats> engineNamesVotesMap = null;
    @JsonProperty("fenMovesInGame")
    private String fenMovesInGame;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("isSingleMove")
    private Boolean isSingleMove;
    @JsonProperty("sizeOfEnginesInFirstGroup")
    private Integer sizeOfEnginesInFirstGroup;
    @JsonProperty("timeout")
    private Integer timeout;
    @JsonProperty("fen")
    private String fen;
    @JsonProperty("typeOfGame")
    private TypeOfMessageExtraction typeOfGame;
    @JsonProperty("answer")
    private String answer;

    public List<String> getChessGameName() {
        return chessGameName;
    }

    public void setChessGameName(List<String> chessGameName) {
        this.chessGameName = chessGameName;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Boolean getIsSingleMove() {
        return isSingleMove;
    }

    public void setIsSingleMove(Boolean singleMove) {
        isSingleMove = singleMove;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public TypeOfMessageExtraction getTypeOfGame() {
        return typeOfGame;
    }

    public void setTypeOfGame(TypeOfMessageExtraction typeOfGame) {
        this.typeOfGame = typeOfGame;
    }

    public String getAnswer() { return answer; }

    public Integer takeAnswerAsInteger() { return Integer.parseInt(answer); }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFenMovesInGame() { return fenMovesInGame; }

    public void setFenMovesInGame(String fenMovesInGame) {
        this.fenMovesInGame = fenMovesInGame;
    }

    public List<EngineEloPair> getChessGamesEloValue() {
        return chessGamesEloValue;
    }

    public void setChessGamesEloValue(List<EngineEloPair> chessGamesEloValue) {
        this.chessGamesEloValue = chessGamesEloValue;
    }

    public List<GameVotingStats> getEngineNamesVotesMap() {
        return engineNamesVotesMap;
    }

    public void setEngineNamesVotesMap(List<GameVotingStats> engineNamesVotesMap) {
        this.engineNamesVotesMap = engineNamesVotesMap;
    }

    public Integer getSizeOfEnginesInFirstGroup() { return sizeOfEnginesInFirstGroup; }

    public Integer takeSizeOfEnginesInSecondGroup() { return chessGameName.size() - sizeOfEnginesInFirstGroup; }

    public void setSizeOfEnginesInFirstGroup(Integer size) {
        this.sizeOfEnginesInFirstGroup = size;
    }
}