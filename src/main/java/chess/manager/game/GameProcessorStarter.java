package chess.manager.game;

import chess.amqp.newAMQP.AMQPReceiverImpl;
import chess.utils.settings.Settings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameProcessorStarter {

    public static void main(String[] argv) {
       try {
           AMQPReceiverImpl receiver = new AMQPReceiverImpl();
           receiver.recvFromQueue(Settings.getChessProcessingQueueName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}