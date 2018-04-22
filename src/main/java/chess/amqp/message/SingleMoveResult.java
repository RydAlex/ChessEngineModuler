package chess.amqp.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "engineName",
        "result"
})
public class SingleMoveResult {

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public String getMoveResult() {
        return moveResult;
    }

    public void setMoveResult(String moveResult) {
        this.moveResult = moveResult;
    }

    @JsonProperty("engineName")
    private String engineName;
    @JsonProperty("moveResult")
    private String moveResult;
}
