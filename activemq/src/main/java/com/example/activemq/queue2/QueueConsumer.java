package com.example.activemq.queue2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueConsumer {

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
             * */
            //非事务消息 --不需要提交（）
            session = connection.createSession(true, Session.SESSION_TRANSACTED);

            //事务消息 session.commmit(),必须提交（session.commit()）
//            session = connection.createSession(true,Session.SESSION_TRANSACTED);

            destination = session.createQueue("queue2");

            MessageConsumer consumer = session.createConsumer(destination);

            while (true) {
                try {
                    TextMessage textMessage = (TextMessage) consumer.receive(100000);
                    if (textMessage != null) {
                        System.out.println("成功接收消息：" + textMessage.getText());
                    } else {
                        break;
                    }
                    // int i = 1/0;
                } catch (Exception e) {
                    e.printStackTrace();
                    session.rollback();
                }

               /* try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //当业务逻辑处理完后，手动确认,挨个确认，比较好资源
                textMessage.acknowledge();*/
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
