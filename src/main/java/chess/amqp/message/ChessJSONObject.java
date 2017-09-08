package chess.amqp.message;

/**
 * Created by aleksanderr on 29/05/17.
 */
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
    @JsonProperty("singleMoveResults")
    private List<SingleMoveResult> singleMoveResults = null;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("isSingleMove")
    private Boolean isSingleMove;
    @JsonProperty("sizeOfEnginesInFight")
    private HashMap<String, Integer> sizeOfEnginesInFight;
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

    public List<SingleMoveResult> getSingleMoveResults() {
        return singleMoveResults;
    }

    public void setSingleMoveResults(List<SingleMoveResult> singleMoveResults) {
        this.singleMoveResults = singleMoveResults;
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

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public HashMap<String, Integer> getSizeOfEnginesInFight() { return sizeOfEnginesInFight; }

    public void addEngineSizeToFirstGrp(Integer size) {
        if(this.sizeOfEnginesInFight == null){
            this.sizeOfEnginesInFight = new HashMap<>();
        }
        this.sizeOfEnginesInFight.put("GroupOneSize", size);
    }

    public void addEngineSizeToSecondGrp(Integer size) {
        if(this.sizeOfEnginesInFight == null){
            this.sizeOfEnginesInFight = new HashMap<>();
        }
        this.sizeOfEnginesInFight.put("GroupSecSize", size);
    }
}