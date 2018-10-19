package chess.amqp.newAMQP;

import chess.redis.RedisAMQPManager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Slf4j
public class AMQPSenderImpl {

    public static void sendMessage(String queueName, String message) throws IOException{
        Channel channel = AMQPConnFactory.createNewConnectionToAMQPQueue(queueName);
        channel.basicPublish("", queueName, null, message.getBytes());
        RedisAMQPManager.increaseInformationAboutMessageInQueue(queueName);
        RedisAMQPManager.getInformationAboutMessageInQueue(queueName);
        log.info("Message to " + queueName + " was published!");
        try {
            Connection connection = channel.getConnection();
            channel.close();
            connection.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
