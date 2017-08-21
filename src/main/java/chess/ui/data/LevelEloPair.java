package chess.ui.data;

import chess.amqp.message.EngineEloPair;
import lombok.Data;

@Data
public class LevelEloPair {
	EngineEloPair eloPair;
	EngineLevel engineLevel;
}
