package chess.amqp.newAMQP;

import chess.utils.settings.Settings;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Optional;

class AMQPConnFactory {

    public static Optional<Channel> createNewConnectionToAMQPQueue(String queueName){
        Optional<Channel> channelOptional = Optional.empty();
        while(!channelOptional.isPresent()){
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setUri(Settings.getAMQPString());
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                channel.basicQos(1);

                channel.queueDeclare(queueName,true,false, false,null);

                channelOptional = Optional.of(channel);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return channelOptional;
    }
}
