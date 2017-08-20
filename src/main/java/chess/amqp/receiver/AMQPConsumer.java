package chess.amqp.receiver;

import chess.utils.json.object.ChessJSONCreator;
import chess.utils.json.object.ChessJSONReader;
import chess.amqp.message.ChessJSONObject;
import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.ForgivingExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import net.jodah.lyra.ConnectionOptions;
import net.jodah.lyra.Connections;
import net.jodah.lyra.config.Config;
import net.jodah.lyra.config.RecoveryPolicies;
import net.jodah.lyra.config.RecoveryPolicy;
import net.jodah.lyra.util.Duration;
import simpleChessManagmentActor.ChessScheduler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class AMQPConsumer {

    private static final String chess_rpc_queue = "ChessRPC";
    private static final String CLOUDAMQP_SYSTEM_URL = "CLOUDAMQP_URL";


    public static void main(String[] argv) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        Connection connection = null;
        try {
            Config config = new Config()
                .withRecoveryPolicy(
                        RecoveryPolicies
                                .recoverAlways()
                                .withInterval(Duration.seconds(5))
                );

            ConnectionOptions connectionOptions = new ConnectionOptions()
                    .withUri(System.getenv(CLOUDAMQP_SYSTEM_URL));
            connection = Connections.create(connectionOptions, config);
            Channel channel = connection.createChannel();

            channel.queueDeclare(chess_rpc_queue, true, false, false, null);
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
                        System.gc();
                    } catch (RuntimeException e){
                        System.out.println(" [.] " + e.toString());
                    } finally {
                        for(int i=0; i<10; i++){
                            try{
                                channel.basicPublish("", properties.getReplyTo(), replyProps, answer.getBytes("UTF-8"));
                                break;
                            } catch (Exception e){
                                System.out.println("I had problem with messaging back the answer");
                                log.error(e.getMessage(), e.getCause());
                                try {
                                    Thread.sleep(30000);
                                } catch (InterruptedException e1) {
                                    System.out.println("what the heck? Even sleep broke O.o ");
                                    log.error(e.getMessage(), e.getCause());
                                }
                            }
                        }
                        channel.basicAck(envelope.getDeliveryTag(), false);
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
        } catch (Exception e) {
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