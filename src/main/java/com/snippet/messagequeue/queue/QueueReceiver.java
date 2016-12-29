package com.snippet.messagequeue.queue;

import com.snippet.messagequeue.MQTypeEnum;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author leixu2
 */
public class QueueReceiver {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "p2pqueue";

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageConsumer consumer;

    public QueueReceiver(MQTypeEnum mqType) {
        switch (mqType) {
            case ACTIVEMQ:
                connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                        ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
                break;
            default:
                System.out.println("MQType not support");
        }
    }

    public void receiveMessage() {
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(QUEUE_NAME);
            consumer = session.createConsumer(destination);
            while (true) {
                TextMessage message = (TextMessage) consumer.receive(100000);
                if (message != null) {
                    System.out.println("ActiveMq Receiver 收到的消息: " + message.getText());
                } else {
                    break;
                }
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        } finally {
            try {
                session.close();
                connection.close();
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        QueueReceiver queueReceiver = new QueueReceiver(MQTypeEnum.ACTIVEMQ);
        queueReceiver.receiveMessage();
    }

}
