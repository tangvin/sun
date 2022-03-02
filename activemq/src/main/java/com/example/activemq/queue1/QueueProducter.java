package com.example.activemq.queue1;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueProducter {
    public static void main(String[] args) {
        String username = "system";

        String password = "manager";

        String brokerURL = "failover://tcp://192.168.188.128:61616";

        ConnectionFactory connectionFactory = null;

        Connection connection = null;

        Session session = null;

        Destination destination = null;

        MessageProducer messageProducer = null;

        connectionFactory = new ActiveMQConnectionFactory(username, password, brokerURL);

        try {
            connection = connectionFactory.createConnection();

            connection.start();

            //非事务消息
//            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);

            //事务消息 session.commmit(),必须提交（session.commit()）
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            Queue zgqueue = session.createQueue("zgqueue");

            MessageProducer producer = session.createProducer(zgqueue);

            for (int i = 0; i < 100; i++) {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText("生产消息测试 ： " + i);
                producer.send(textMessage);
                System.out.println("发送成功：" + textMessage.getText());
            }

            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
