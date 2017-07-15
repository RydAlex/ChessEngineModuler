package AMQPManagment.AMQPConsumer;

import AMQPManagment.utils.chessJSONParsers.ChessJSONCreator;
import AMQPManagment.utils.chessJSONParsers.ChessJSONReader;
import AMQPManagment.utils.data.ChessJSONObject;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import simpleChessManagmentActor.ChessScheduler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class AMQPConsumer {

    private static final String chess_rpc_queue = "ChessRPC";
    public static final String CLOUDAMQP_SYSTEM_URL = "CLOUDAMQP_URL";


    public static void main(String[] argv) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(System.getenv(CLOUDAMQP_SYSTEM_URL));
	System.out.println("test 0451");
	System.out.flush();
        Connection connection = null;
        try {
            System.out.println("I will start connection in a moment");
            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(chess_rpc_queue, false, false, false, null);
            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(properties.getCorrelationId())
                            .build();

                    ChessJSONObject response;
                    String answer = "";

                    try {
                        String message = new String(body,"UTF-8");
                        ChessJSONObject chessObject = ChessJSONReader.readDataFromJson(message);
                        log.info("I process " + chessObject.getChessGameName() + " with fen " + chessObject.getFen());
                        if(chessObject.getDepth() != null) {
                            response = ChessScheduler.startGameWithDepthRule(chessObject);
                        }
                        else if(chessObject.getTimeout() != null) {
                            response = ChessScheduler.startGameWithTimeoutRule(chessObject);
                        } else {
                            response = null;
                        }
                        log.info("I have message ready To Parse And Send");
                        answer = ChessJSONCreator.createChessJsonFromObject(response);
                    }
                    catch (RuntimeException e){
                        System.out.println(" [.] " + e.toString());
                    }
                    finally {
                        try{
                            channel.basicPublish("", properties.getReplyTo(), replyProps, answer.getBytes("UTF-8"));
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        } catch (Exception e){
                            System.out.println("I had problem with messeging back the answer");
                        }
                    }
                }
            };

            channel.basicConsume(chess_rpc_queue, false, consumer);

            //loop to prevent reaching finally block
            while(true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException _ignore) {
                    System.out.println("nooope");
                }
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (IOException _ignore) {}
        }
    }
}
