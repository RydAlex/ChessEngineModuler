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
@Getter
@Setter
public class ChessJSONObject {

    @JsonProperty("chessGameName")
    private List<String> chessGameName;
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