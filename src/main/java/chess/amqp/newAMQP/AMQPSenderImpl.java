package chess.amqp.newAMQP;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Slf4j
public class AMQPSenderImpl {

    public static void sendMessage(String queueName, String message) throws IOException {
        Optional<Channel> opt = AMQPConnFactory.createNewConnectionToAMQPQueue(queueName);
        if(opt.isPresent()){
            Channel channel = opt.get();
            channel.basicPublish("", queueName, null, message.getBytes());
            RedisManager.increaseInformationAboutMessageInQueue(queueName);
            RedisManager.getInformationAboutMessageInQueue(queueName);
            log.info("Message to " + queueName + " was published!");
            try {
                channel.close();
                channel.getConnection().close();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        } else {
            log.info("I had a problem with send message to queue " + queueName);
        }

    }
}
