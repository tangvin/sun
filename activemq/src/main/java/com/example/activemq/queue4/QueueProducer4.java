package com.example.activemq.queue4;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;

import javax.jms.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;

/**
 * shangchangzhe
 */
public class QueueProducer4 {

    public static void main(String[] args) {
        //连接信息设置
        String username = "system";
        String password = "manager";
//        String brokerURL = "failover:(tcp://192.168.88.131:61616?wireFormat.maxInactivityDuration=0&maxInactivityDurationInitalDelay=30000)?randomize=false&priorityBackup=true";
        String brokerURL = "failover:(tcp://192.168.188.128:61616)";
        //连接工厂
        ActiveMQConnectionFactory connectionFactory = null;
        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session = null;
        //消息的目的地
        Destination destination = null;
        //消息生产者
        MessageProducer messageProducer = null;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(username, password, brokerURL);

        ActiveMQPrefetchPolicy pp = new ActiveMQPrefetchPolicy();
        pp.setQueuePrefetch(1);
        //消费策略，一条一条的去broker里面去取消息消费
        ((ActiveMQConnectionFactory) connectionFactory).setPrefetchPolicy(pp);

        //异步发送
        ((ActiveMQConnectionFactory) connectionFactory).setUseAsyncSend(true);

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个名称为QueueTest的消息队列
//            destination = session.createQueue("ActiveMQ.Statistics.Broker");
            destination = session.createQueue("zgqueue");

            //创建消息生产者
            messageProducer = session.createProducer(destination);
//            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            //发送消息
            TextMessage message = null;

            /*
            * //纯字符串的数据
            session.createTextMessage();
            //序列化的对象
            session.createObjectMessage();
            //流，可以用来传递文件等
            session.createStreamMessage();
            //用来传递字节
            session.createBytesMessage();
            //这个方法创建出来的就是一个map，可以把它当作map来用，当你看了它的一些方法，你就懂了
            session.createMapMessage();
            //这个方法，拿到的是javax.jms.Message，是所有message的接口
            session.createMessage();
            * */

            /*for (int i=0; i<10; i++) {
                //创建要发送的文本信息
                message = session.createTextMessage("Queue消息测试" +(i+1));
                //通过消息生产者发出消息
                messageProducer.send(message);
                System.out.println(Thread.currentThread().getName() + "发送成功：" + message.getText());
//                throw new RuntimeException();
            }*/
            textMessage(session, messageProducer);
            objectMessage(session, messageProducer);
//            mapMessage(session, messageProducer);
//            streamMessage(session,messageProducer);
//            byteMessage(session,messageProducer);
//            session.commit();

        } catch (Exception e) {
            e.printStackTrace();

//如果是事务消息，消息方式异常时回滚
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
                textMessage.setText("测试消息：" + i);
                textMessage.setIntProperty("age", Integer.parseInt("19" + i));
                textMessage.setStringProperty("name", "jack" + i);
                producer.send(textMessage);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void objectMessage(Session session, MessageProducer producer) {
        try {
            ObjectMessage objectMessage = session.createObjectMessage();
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setPassword("123");
                user.setUsername("jack");
                objectMessage.setObject(user);
                producer.send(objectMessage);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void mapMessage(Session session, MessageProducer producer) {
        try {
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("username", "jack");
            producer.send(mapMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void streamMessage(Session session, MessageProducer producer) {
        try {
            StreamMessage streamMessage = session.createStreamMessage();
            streamMessage.writeString("i am jack");
            producer.send(streamMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void byteMessage(Session session, MessageProducer producer) {
        FileInputStream is = null;
        try {
            BytesMessage bytesMessage = session.createBytesMessage();
            replyMessage(session, "bytemessage_reply_queue", bytesMessage);
            bytesMessage.setStringProperty("filename", "my.ini");
            File file = new File("D:" + File.separator + "Soft" + File.separator + "Test" + File.separator + "my.ini");
            is = new FileInputStream(file);

            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            bytesMessage.writeBytes(buffer);
            producer.send(bytesMessage);
            System.out.println("send succeese!");
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //消息回复地址设置
    private static void replyMessage(Session session, String queuename, Message message) {
        try {
            Queue queue = session.createQueue(queuename);
            message.setJMSReplyTo(queue);

            MessageConsumer consumer = session.createConsumer(queue);

            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message != null && message instanceof TextMessage) {
                        try {
                            System.out.println("reply message : " + ((TextMessage) message).getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    } else if (message != null && message instanceof MapMessage) {
                        System.out.println("统计消息~~~");
                        MapMessage reply = (MapMessage) message;
                        try {
                            for (Enumeration e = reply.getMapNames(); e.hasMoreElements(); ) {
                                String name = e.nextElement().toString();
                                System.out.println(name + "=" + reply.getObject(name));
                            }
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
