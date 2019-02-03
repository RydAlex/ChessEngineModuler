package chess.amqp.actions;

import chess.amqp.message.ChessJSONObject;
import chess.amqp.newAMQP.AMQPSenderImpl;
import chess.utils.json.object.ChessJSONCreator;
import chess.utils.json.object.ChessJSONReader;
import chess.utils.settings.Settings;
import lombok.extern.slf4j.Slf4j;
import simpleChessManagmentActor.ChessScheduler;

@Slf4j
public class ChessGameProcessingAction implements Action<String>{

    @Override
    public String proceed(String message) {
        String answer;
        ChessJSONObject chessObject = ChessJSONReader.readDataFromJson(message);
        if (chessObject.getTimeout() != null) {
//            chessObject.setAnswer("1");
            chessObject = ChessScheduler.startGameWithTimeoutRule(chessObject);
        } else {
            chessObject = null;
        }

        log.info("I have message ready To Parse And Send");
        answer = ChessJSONCreator.createChessJsonFromObject(chessObject);
        System.gc();
        for (int i = 0; i < 10; i++){
            try {
                AMQPSenderImpl.sendMessage(Settings.getChessSavingQueueName(), answer);
                break;
            } catch (Exception e) {
                log.info("I tried send return message " + i + " time. However i couldn't");
            }
        }
        return null;
    }
}
