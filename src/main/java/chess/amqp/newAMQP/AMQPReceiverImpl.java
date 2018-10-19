package chess.amqp.newAMQP;

import chess.amqp.actions.Action;
import chess.amqp.actions.ActionProcessorFactory;
import chess.redis.RedisAMQPManager;
import chess.redis.RedisManager;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class AMQPReceiverImpl {
    private String queueName;

    public void recvFromQueue(String queueName) {
        Channel channel = AMQPConnFactory.createNewConnectionToAMQPQueue(queueName);
        this.queueName = queueName;
        System.out.println(" [x] Awaiting RPC requests");
        try {
            channel.basicConsume(queueName, false, createConsumer(channel));
            while (true) {
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Consumer createConsumer(Channel channel) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    String message = new String(body, "UTF-8");
                    System.out.println("Message is: " + message);
                    Action action = ActionProcessorFactory.create(queueName);
                    if(action != null){
                        action.proceed(message);
                    }
                } catch (RuntimeException e) {
                    System.out.println(" [.] " + e.toString());
                } finally {
                    RedisAMQPManager.reduceInformationAboutMessageInQueue(queueName);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
    }
}
