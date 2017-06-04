package AMQPManagment.utils.data;

/**
 * Created by aleksanderr on 29/05/17.
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "chessGameName",
        "depth",
        "timeout",
        "fen",
        "answer"
})
@Getter
@Setter
public class ChessJSONObject {

    @JsonProperty("chessGameName")
    private String chessGameName;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("timeout")
    private Integer timeout;
    @JsonProperty("fen")
    private String fen;
    @JsonProperty("answer")
    private String answer;
}