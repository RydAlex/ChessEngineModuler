package chess.ui.data;

import chess.amqp.message.TypeOfMessageExtraction;
import lombok.Data;

import java.util.List;

@Data
public class GameSpecification {
	private List<String> gameEngines;
	private String rule;
	private TypeOfMessageExtraction type;
	private String chessboardFen;
}
