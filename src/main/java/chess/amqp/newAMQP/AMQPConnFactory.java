package chess.amqp.newAMQP;

import chess.utils.settings.Settings;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

class AMQPConnFactory {

    public static Channel createNewConnectionToAMQPQueue(String queueName){
        Channel channel = null;
        int connectionTry = 0;
        while(channel == null){
            connectionTry++;
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setUri(Settings.getAMQPString());
                Connection connection = factory.newConnection();
                channel = connection.createChannel();
                channel.basicQos(1);
                channel.queueDeclare(queueName,true,false, false,null);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            if(connectionTry >= 10){
                throw new RuntimeException("Cannot connect to AMQP");
            }
        }
        return channel;
    }
}
