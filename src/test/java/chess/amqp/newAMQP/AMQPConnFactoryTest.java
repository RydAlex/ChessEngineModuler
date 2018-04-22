package chess.amqp.newAMQP;

import org.junit.Test;

public class AMQPConnFactoryTest {

    @Test
    public void testAMQPFactory(){
        AMQPConnFactory.createNewConnectionToAMQPQueue("testQueue");
    }
}