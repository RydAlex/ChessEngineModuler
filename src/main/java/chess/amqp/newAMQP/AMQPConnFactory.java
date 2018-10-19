package chess.amqp.newAMQP;

import chess.utils.settings.Settings;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Optional;

class AMQPConnFactory {

    public static Channel createNewConnectionToAMQPQueue(String queueName){
        Channel channel = null;
        while(channel == null){
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setUri(Settings.getAMQPString());
                Connection connection = factory.newConnection();
                channel = connection.createChannel();
                channel.basicQos(1);

                channel.queueDeclare(queueName,true,false, false,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return channel;
    }
}
