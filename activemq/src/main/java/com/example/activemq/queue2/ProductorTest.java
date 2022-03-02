package com.example.activemq.queue2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProductorTest {
    public static void main(String[] args) {
        String username = "system";

        String password = "manager";

        String brokerURL = "failover://tcp://192.168.188.128:61618";

        ConnectionFactory connectionFactory = null;

        Connection connection = null;

        Session session = null;

        Destination destination = null;

        MessageProducer messageProducer = null;

        connectionFactory = new ActiveMQConnectionFactory(username, password, brokerURL);

        try {
            connection = connectionFactory.createConnection();

            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue zgqueue = session.createQueue("productorTest");

            MessageProducer producer = session.createProducer(zgqueue);
            //默认是一个持久化的消息存储机制
//          producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            textMessage(session, producer);
            objectMessage(session, producer);
//            byteMessage(session, producer);

//            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();

            try {
                session.rollback();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }

        }
    }

    private static void textMessage(Session session, MessageProducer producer) {
        try {
            TextMessage textMessage = session.createTextMessage();
            for (int i = 0; i < 10; i++) {
                textMessage.setText("生产消息测试 ： " + i);
//                textMessage.setIntProperty("age",Integer.parseInt("19" + i));
//                textMessage.setStringProperty("name","jack" + i);
                producer.send(textMessage);
                System.out.println("发送成功：" + textMessage.getText());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void objectMessage(Session session, MessageProducer producer) {
        try {
            ObjectMessage objectMessage = session.createObjectMessage();
//            objectMessage.setJMSReplyTo();
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setUsername("jack" + i);
                user.setPassword("123" + i);
                objectMessage.setObject(user);
                producer.send(objectMessage);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

//    private static void byteMessage(Session session, MessageProducer producer) {
//        FileInputStream is = null;
//        try {
//            BytesMessage bytesMessage = session.createBytesMessage();
//            replyMessage(session, "bytemsg_reply_queue", bytesMessage);
//            bytesMessage.setStringProperty("filename", "my.ini");
//            File file = new File("D:" + File.separator + "software" + File.separator + "my.ini");
//
//            is = new FileInputStream(file);
//
//            byte[] buffer = new byte[is.available()];
//            is.read(buffer);
//
//            bytesMessage.writeBytes(buffer);
//            producer.send(bytesMessage);
//            System.out.println("send to broker!!");
//        } catch (JMSException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (null != is) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private static void streamMessage(Session session, MessageProducer producer) {
//        try {
//            StreamMessage streamMessage = session.createStreamMessage();
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }

//    private static void replyMessage(Session session, String queuename, Message message) {
//        try {
//            Queue queue = session.createQueue(queuename);
//            message.setJMSReplyTo(queue);
//
//
//            MessageConsumer consumer = session.createConsumer(queue);
//            consumer.setMessageListener(new MessageListener() {
//                @Override
//                public void onMessage(Message message) {
//                    if (message != null && message instanceof TextMessage) {
//                        try {
//                            System.out.println("reply message : " + ((TextMessage) message).getText());
//                        } catch (JMSException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
}
