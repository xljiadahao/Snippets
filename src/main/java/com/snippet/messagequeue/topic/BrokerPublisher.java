package com.snippet.messagequeue.topic;

import com.snippet.messagequeue.MQTypeEnum;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author leixu2
 */
public class BrokerPublisher {
    
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "topicbroker";

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;
    
    public BrokerPublisher(MQTypeEnum mqType) {
        switch (mqType) {
            case ACTIVEMQ:
                connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                        ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
                break;
            default:
                System.out.println("MQType not support");
        }

    }

    public void sendMessage(String msg) {
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(TOPIC_NAME);
            producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage message = session.createTextMessage(msg);
            message.setText("ActiveMq BrokerPublisher 发送的消息: " + msg);
            System.out.println("ActiveMq BrokerPublisher 发送的消息: " + msg);
            producer.send(message);
            session.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    public static void main(String[] args) {
        BrokerPublisher brokerPublisher = new BrokerPublisher(MQTypeEnum.ACTIVEMQ);
        brokerPublisher.sendMessage("Broker MSG, Topic: " + TOPIC_NAME);
    }

}
