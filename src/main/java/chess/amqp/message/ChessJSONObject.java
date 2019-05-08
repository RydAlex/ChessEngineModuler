package chess.amqp.message;

import chess.utils.ChessClusterBattle;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "clusterBattle",
        "timeout",
        "fen",
        "answer"
})
public class ChessJSONObject {

    @JsonProperty("clusterBattle")
    private ChessClusterBattle clusterBattle;
    @JsonProperty("timeout")
    private Integer timeout;
    @JsonProperty("fen")
    private String fen;
    @JsonProperty("game_moves")
    private String gameMoves;
    @JsonProperty("answer")
    private String answer;


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

    public String getAnswer() { return answer; }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer takeAnswerAsInteger() { return Integer.parseInt(answer); }

    public ChessClusterBattle getClusterBattle() {
        return clusterBattle;
    }

    public void setClusterBattle(ChessClusterBattle clusterBattle) {
        this.clusterBattle = clusterBattle;
    }

    public String getGameMoves() {
        return gameMoves;
    }

    public void setGameMoves(String gameMoves) {
        this.gameMoves = gameMoves;
    }
}