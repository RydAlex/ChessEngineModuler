package chess.amqp.sender;

import chess.utils.json.object.ChessJSONReader;
import chess.amqp.message.ChessJSONObject;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by aleksanderr on 11/06/17.
 */

@Slf4j
class AMQPSenderConnImpl {

    public static final String CLOUDAMQP_SYSTEM_URL = "CLOUDAMQP_URL";
    private Connection connection;
    private Channel channel;
    private String chess_rpc_queue = "ChessRPC";
    private String replyQueueName;

    private AMQPSenderConnImpl() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(System.getenv(CLOUDAMQP_SYSTEM_URL));

        connection = factory.newConnection();
        channel = connection.createChannel();

        replyQueueName = channel.queueDeclare().getQueue();

        channel.queueDeclare(chess_rpc_queue, false, false, false, null);
        channel.basicQos(1);
    }

    private List<ChessJSONObject> sendRequestToEngine(List<String> messages) throws Exception {
        final BlockingQueue<String> responses = new ArrayBlockingQueue<>(messages.size());
        LinkedList<ChessJSONObject> returnData = new LinkedList<>();
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
                        String g = new String(body, "UTF-8");
                        System.out.println("i have answer from Engine " + g);
                        responses.add(g);
                    }
                });
            } catch (Exception e) {
                log.info(e.getMessage());
                e.printStackTrace();
            }
        });
        int i=0;
        while(responses.isEmpty())
        {
            Thread.sleep(100);
        }
        returnData.add(ChessJSONReader.readDataFromJson(responses.take()));
        return returnData; // pobranie elementu z czekaniem az jakis sie pojawi.
    }

    private void close() throws IOException {
        connection.close();
    }

    static List<ChessJSONObject> sendMessages(List<String> jsons) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        AMQPSenderConnImpl sender = null;
        List<ChessJSONObject> response = null;
        try {
            sender = new AMQPSenderConnImpl();
            System.out.println("Sending chess JSONs" );

            response = sender.sendRequestToEngine(jsons);
            System.out.println(" [.] Got '" + response + "'");
            return response;
        }
        catch  (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (sender!= null) {
                try {
                    sender.close();
                    return response;
                }
                catch (IOException _ignore) {

                }
            }
        }
        return null;
    }
}
