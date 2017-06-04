package AMQPManagment.AMQPManager;

import java.io.IOException;

/**
 * Created by aleksanderr on 22/05/17.
 */
import AMQPManagment.utils.EngineSearcher;
import AMQPManagment.utils.chessJSONParsers.ChessJSONCreator;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import engineprocessor.core.enginemechanism.FenGenerator;
import engineprocessor.interfaces.EngineRunnerImpl;
import simpleChessManagmentActor.GameMultipleEngineSetCreator;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class AMQPSender {

    private Connection connection;
    private Channel channel;
    private String chess_rpc_queue = "ChessRPC";
    private String replyQueueName;

    private AMQPSender() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://hfmmtwsb:nAuNrdEXLlQ4Y1EllD10yngQf56f5cyM@zebra.rmq.cloudamqp.com/hfmmtwsb");

        connection = factory.newConnection();
        channel = connection.createChannel();

        replyQueueName = channel.queueDeclare().getQueue();
    }

    private List<String> call(List<String> messages) throws Exception {
        final BlockingQueue<String> response = new ArrayBlockingQueue<>(messages.size());
        LinkedList<String> returnData = new LinkedList<>();
        messages.stream().parallel().forEach(message -> {
            try {
                String corrId = UUID.randomUUID().toString();

                AMQP.BasicProperties props = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(corrId)
                        .replyTo(replyQueueName)
                        .build();

                channel.basicPublish("", chess_rpc_queue, props, message.getBytes("UTF-8"));
                channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        System.out.println("i am here" + properties.getCorrelationId());
                        String g = new String(body, "UTF-8");
                        System.out.println("i add the message to List");
                        response.add(g);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        while(true)
        {
            Thread.sleep(2);
            if(response.size()==4) break;
        }
        return returnData; // pobranie elementu z czekaniem az jakis sie pojawi.
    }

    private void close() throws IOException {
        connection.close();
    }


    public static void main(String[] argv) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        AMQPSender fibonacciRpc = null;
        List<String> response = null;
        List<String> jsons = ChessJSONCreator.createChessJsonWithDepthRule(
                new FenGenerator().returnFenStringPositions(),
                7,
                EngineSearcher.searchFewRandomEngineNames(2)
        );
        try {
            fibonacciRpc = new AMQPSender();
            System.out.println(" Sending chess JSONs");

            response = fibonacciRpc.call(jsons);

            System.out.println(" [.] Got '" + response + "'");
        }
        catch  (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (fibonacciRpc!= null) {
                try {
                    fibonacciRpc.close();
                }
                catch (IOException _ignore) {}
            }
        }
    }
}