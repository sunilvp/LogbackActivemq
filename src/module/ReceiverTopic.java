package module;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import ch.qos.logback.classic.spi.LoggingEventVO;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;

import java.io.Serializable;

public class ReceiverTopic {
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destinationTopic = null;
    private MessageConsumer consumer = null;

    public ReceiverTopic() {

    }

    public void receiveMessageTopic() {
        try {
            factory = new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destinationTopic = session.createTopic("myTopic");
            ConsumerTopic lConsumerTopic1 = new ConsumerTopic(destinationTopic, "Consumer-Topic-1");
            Thread lThread1 = new Thread(lConsumerTopic1);

            lThread1.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    class ConsumerTopic implements Runnable{

        Destination destination_;
        String consumerName_;
        MessageConsumer consumer_;

        ConsumerTopic(Destination aInDestination, String aInConsumerName) throws JMSException {
            destination_ = aInDestination;
            consumer_ = session.createConsumer(destination_);
            consumerName_ = aInConsumerName;
        }

        @Override
        public void run() {
            System.out.println("Starting the Receive Thread \t"+ consumerName_);
            while (true){
                Message message = null;
                try {
                    message = consumer_.receive();
                    ActiveMQObjectMessage lMsg = (ActiveMQObjectMessage)message;
                    Serializable e = lMsg.getObject();
                    if(e instanceof LoggingEventVO) {
                        LoggingEventVO l =((LoggingEventVO)e);
                        System.out.println("Printing the message from consumer: "+((LoggingEventVO)e).getFormattedMessage());
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                    break;
                }
            }
            System.out.println("Exiting the Receive Thread \t"+consumerName_);
            try {
                consumer_.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}