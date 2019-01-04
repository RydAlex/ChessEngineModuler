package chess.amqp.newAMQP;

import chess.amqp.actions.Action;
import chess.amqp.actions.ActionProcessorFactory;
import chess.redis.RedisAMQPManager;
import com.rabbitmq.client.*;

import java.io.IOException;

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
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                try {
                    String message = new String(body, "UTF-8");
                    System.out.println("Message is: " + message);
                    Action action = ActionProcessorFactory.create(queueName);
                    if(action != null){
                        action.proceed(message);
                    }
                } catch (Exception e) {
                    System.out.println(" [.] " + e.toString());
                    throw new RuntimeException();
                } finally {
                    try {
                        RedisAMQPManager.reduceInformationAboutMessageInQueue(queueName, channel, envelope);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }
}
