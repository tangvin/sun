package com.example.activemq.queue2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ConsumerTest {

    public static void main(String[] args) {
        String username = "system";

        String password = "manager";

        String brokerURL = "failover://nio://192.168.188.128:61617";

        ConnectionFactory connectionFactory = null;

        Connection connection = null;

        Session session = null;

        Destination destination = null;

        MessageProducer messageProducer = null;

        connectionFactory = new ActiveMQConnectionFactory(username, password, brokerURL);

        try {
            connection = connectionFactory.createConnection();

            connection.start();

            /*
             * 第一个参数：是否支持事务，如果是true的话，第二个参数就会忽略
             *
             * 第二个参数：消息确认机制
             *
             * Session.AUTO_ACKNOWLEDGE：自动确认消息，consumer.receive只要有返回就确认成功处理消息，哪怕处理消息
             * 出现异常也是给了一个成功的应答
             *
             * Session.CLIENT_ACKNOWLEDGE:客户端接收消息后，必须调用ackknowledge方法手动确认，手动确认才会删除
             *
             * Session.DUPS_OK_ACKNOWLEDGE:批量确认消息
             * DUPS_OK_ACKNOWLEDGE = AUTO_ACKNOWLEDGE + 延迟
             *
             *
             * 1、如果生产者是事务消息，那么消费者也要保证是事务消息
             *
             * */
            session = connection.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);

            destination = session.createQueue("productorTest");

            MessageConsumer consumer = session.createConsumer(destination);

            final Session finalSession = session;
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        textMessage((TextMessage) message);
                    } else if (message instanceof ObjectMessage) {
                        objectMessage((ObjectMessage) message);
                    } else if (message instanceof BytesMessage) {
                        bytesMessage((BytesMessage) message, finalSession);
                    }
                }
            });

            /*while (true) {

                try {
                    TextMessage textMessage = (TextMessage) consumer.receive(100000);

                    if (textMessage != null) {
                        System.out.println("成功接收消息：" + textMessage.getText());
                    } else {
                        break;
                    }

                    textMessage.acknowledge();

//                    int i = 1 / 0;
                } catch (Exception e) {
                    e.printStackTrace();

                    *//*
             * ActiveMQ.DLQ 如果rollback成功，会把消息存储在DLQ队列
             * 1、过期消息
             * 2、处理失败消息
             * 3、回滚消息
             *
             * *//*
                    session.rollback();
                }

               *//* try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //当业务逻辑处理完后，手动确认,挨个确认，比较好资源
                textMessage.acknowledge();*//*
            }*/
        } catch (
                JMSException e) {
            e.printStackTrace();
        }
    }

    private static void textMessage(TextMessage textMessage) {
        try {

            System.out.println("成功消费消息：" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void objectMessage(ObjectMessage objectMessage) {
        try {
            User user = (User) objectMessage.getObject();
            System.out.println(user.getUsername() + ":" + user.getPassword());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void bytesMessage(BytesMessage bytesMessage, Session session) {
        FileOutputStream os = null;
        try {
            String filename = bytesMessage.getStringProperty("filename");
            os = new FileOutputStream("F:" + File.separator + filename);
            byte[] bytes = new byte[1024];

            int len = 0;
            while ((len = bytesMessage.readBytes(bytes)) != -1) {
                os.write(bytes, 0, len);
            }

            System.out.println("文件保存成功！！");

            Destination jmsReplyTo = bytesMessage.getJMSReplyTo();
            TextMessage textMessage = session.createTextMessage("文件处理成功回执消息：" + filename);
            MessageProducer producer = session.createProducer(jmsReplyTo);
            producer.send(textMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
