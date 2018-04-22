package chess.amqp.newAMQP;

import chess.amqp.actions.Action;
import chess.amqp.actions.ActionProcessorFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Optional;

public class AMQPReceiverImpl {
    private String queueName;

    public void recvFromQueue(String queueName) {
        Optional<Channel> opt = AMQPConnFactory.createNewConnectionToAMQPQueue(queueName);
        this.queueName = queueName;
        if (opt.isPresent()) {
            System.out.println(" [x] Awaiting RPC requests");
            try {
                opt.get().basicConsume(queueName, false, createConsumer(opt.get()));
                while (true) {
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println();
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
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    int before =  RedisManager.getInformationAboutMessageInQueue(queueName);
                    RedisManager.reduceInformationAboutMessageInQueue(queueName);
                    System.out.println("Redis amount for Chess changed: " + before + "-->" + RedisManager.getInformationAboutMessageInQueue(queueName));
                }
            }
        };
    }
}
