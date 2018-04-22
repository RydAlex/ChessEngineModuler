package chess.amqp.newAMQP;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class AMQPSenderImplTest {

    @Test
    public void sendMessage() {
        try {
            for(int i=0 ; i<10 ; i++){
                AMQPSenderImpl.sendMessage("Chess", "Hello its me " + i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void recvMessage() {
        AMQPReceiverImpl receiver = new AMQPReceiverImpl();
        receiver.recvFromQueue("Chess");
    }
}