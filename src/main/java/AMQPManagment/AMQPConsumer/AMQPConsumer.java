package AMQPManagment.AMQPConsumer;

import AMQPManagment.utils.chessJSONParsers.ChessJSONCreator;
import AMQPManagment.utils.chessJSONParsers.ChessJSONReader;
import AMQPManagment.utils.data.ChessJSONObject;
import com.rabbitmq.client.*;
import engineprocessor.core.enginemechanism.EngineProcessor;
import engineprocessor.interfaces.EngineRunner;
import engineprocessor.interfaces.EngineRunnerImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class AMQPConsumer {

    private static final String RPC_QUEUE_NAME = "ChessRPC";

    private static int fib(int n) {
        if (n ==0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }

    public static void main(String[] argv) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://hfmmtwsb:nAuNrdEXLlQ4Y1EllD10yngQf56f5cyM@zebra.rmq.cloudamqp.com/hfmmtwsb");

        Connection connection = null;
        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(properties.getCorrelationId())
                            .build();

                    String response = "";
                    String answer = "";

                    try {
                        String message = new String(body,"UTF-8");
                        ChessJSONObject chessObject = ChessJSONReader.readDataFromJson(message);
                        log.info("I process " + chessObject.getChessGameName() + " with fen" + chessObject.getFen());
                        EngineRunner engine = new EngineRunnerImpl();
                        if(chessObject.getDepth() != null) {
                            response = engine.RunEngineWithGoDepthCommand(
                                    chessObject.getChessGameName(),
                                    chessObject.getFen(),
                                    chessObject.getDepth());
                        }
                        else if(chessObject.getTimeout() != null) {
                            response = engine.RunEngineWithGoTimeoutCommand(
                                    chessObject.getChessGameName(),
                                    chessObject.getFen(),
                                    chessObject.getTimeout());
                        } else {
                            response = null;
                        }
                        log.info("Answer from this message is " + response);
                        chessObject.setAnswer(response);
                        answer = ChessJSONCreator.createChessJsonFromObject(chessObject);
                    }
                    catch (RuntimeException e){
                        System.out.println(" [.] " + e.toString());
                    }
                    finally {
                        channel.basicPublish("", properties.getReplyTo(), replyProps, answer.getBytes("UTF-8"));

                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

            //loop to prevent reaching finally block
            while(true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException _ignore) {}
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