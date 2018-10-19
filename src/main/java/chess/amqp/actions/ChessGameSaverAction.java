package chess.amqp.actions;

import chess.amqp.message.ChessJSONObject;
import chess.manager.game.answer.processor.AnswerProcessor;
import chess.utils.json.object.ChessJSONReader;

public class ChessGameSaverAction implements Action<String> {
    @Override
    public String proceed(String message) {
        ChessJSONObject chessObject = ChessJSONReader.readDataFromJson(message);
        AnswerProcessor.processAnswer(chessObject);

        return "";
    }
}
