package com.snippet.messagequeue.topic;

import com.snippet.messagequeue.MQTypeEnum;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author leixu2
 */
public class BrokerSubscriber {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "topicbroker";

    private ConnectionFactory connectionFactory;
    private Connection connection = null;
    private Session session;
    private Destination destination;
    private MessageConsumer consumer;

    public BrokerSubscriber(MQTypeEnum mqType) {
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
            Topic topic = session.createTopic(TOPIC_NAME);
            // ActiveMQTopic topic= new ActiveMQTopic(TOPIC_NAME);
            consumer = session.createConsumer(topic);
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    TextMessage tm = (TextMessage) message;
                    try {
                        System.out.println("BrokerSubscriber 监听到的消息: " + tm.getText());
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
//            while (true) {
//                TextMessage message = (TextMessage) consumer.receive(10000);
//                if (message != null) {
//                    System.out.println("BrokerSubscriber 监听到的消息: " + message.getText());
//                } else {
//                    continue;
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void closeListener() {
        try {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Throwable ignore) {
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BrokerSubscriber brokerSubscriber = new BrokerSubscriber(MQTypeEnum.ACTIVEMQ);
        brokerSubscriber.receiveMessage();
        // listening to the topic for 20sec
        Thread.sleep(20000);
        brokerSubscriber.closeListener();
    }

}
