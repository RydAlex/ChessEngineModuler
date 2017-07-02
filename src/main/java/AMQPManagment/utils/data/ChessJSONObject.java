package AMQPManagment.utils.data;

/**
 * Created by aleksanderr on 29/05/17.
 */
import AMQPManagment.utils.TypeOfMessageExtraction;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "chessGameName",
        "depth",
        "timeout",
        "fen",
        "typeOfGame",
        "answer"
})
public class ChessJSONObject {

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @JsonProperty("chessGameName")
    private List<String> chessGameName;
    @JsonProperty("singleMoveResults")
    private List<SingleMoveResult> singleMoveResults = null;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("isSingleMove")
    private Boolean isSingleMove;
    @JsonProperty("timeout")
    private Integer timeout;
    @JsonProperty("fen")
    private String fen;
    @JsonProperty("typeOfGame")
    private TypeOfMessageExtraction typeOfGame;
    @JsonProperty("answer")
    private String answer;
}