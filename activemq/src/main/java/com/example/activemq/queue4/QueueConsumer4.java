package com.example.activemq.queue4;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: 烛光学院Jack老师
 * @Date: 2018/11/9 13:49
 * @Version 1.0
 * @todo
 */
public class QueueConsumer4 {
    public static void main(String[] args) {
        //连接信息设置
        String username = "system";
        String password = "manager";
//        String brokerURL = "failover:(tcp://192.168.88.131:61616)?randomize=false&priorityBackup=true";
        String brokerURL = "failover:(tcp://192.168.188.128:61616)";
        //连接工厂
        ConnectionFactory connectionFactory = null;
        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session = null;
        //消息的目的地
        Destination destination = null;
        //消息消费者
        MessageConsumer messageConsumer = null;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(username, password, brokerURL);

        String selector = "age > 192 and name = 'jack8'";

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            //第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            //第一个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            //Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
            //Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
            //DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
            /*"消息可重复"确认，意思是此模式下，可能会出现重复消息，并不是一条消息需要发送多次ACK才行。它是一种潜在的"AUTO_ACK"确认机制，为批量确认而生，而且具有“延迟”确认的特点。对于开发者而言，这种模式下的代码结构和AUTO_ACKNOWLEDGE一样，不需要像CLIENT_ACKNOWLEDGE那样调用acknowledge()方法来确认消息。*/
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个连接QueueTest的消息队列
            destination = session.createQueue("zgqueue");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);

            final Session finalSession = session;
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        textMessage((TextMessage) message);
                    } else if (message instanceof ObjectMessage) {
                        objectMessage((ObjectMessage) message);
                    } else if (message instanceof BytesMessage) {
                        bytesMessage((BytesMessage) message, finalSession);
                    } else if (message instanceof MapMessage) {
                        mapMessage((MapMessage) message);
                    } else if (message instanceof StreamMessage) {
                        streamMessage((StreamMessage) message);
                    }
                }
            });

            /*while (true) {
                try {
                    TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                    if (textMessage != null) {
                        System.out.println("成功接收消息1:" + textMessage.getText());
                    } else {
                        break;
                    }
//                    int i = 1 / 0;
                }catch (Exception e) {
                    e.printStackTrace();

                    session.rollback();
                }
            }*/
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void textMessage(TextMessage textMessage) {
        try {
            System.out.println("成功接收：" + textMessage.getText() + "-->" + textMessage.getIntProperty("age"));
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

    private static void mapMessage(MapMessage mapMessage) {
        try {
            System.out.println(mapMessage.getString("username"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void streamMessage(StreamMessage streamMessage) {
        try {
            System.out.println(streamMessage.readString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void bytesMessage(BytesMessage bytesMessage, Session session) {
        FileOutputStream os = null;
        try {
            String filename = bytesMessage.getStringProperty("filename");
            os = new FileOutputStream("D:" + File.separator + filename);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bytesMessage.readBytes(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            System.out.println("文件保存成功！！");

            Destination replyto = bytesMessage.getJMSReplyTo();
            TextMessage textMessage = session.createTextMessage("file handler succsses:" + filename);
            MessageProducer producer = session.createProducer(replyto);
            producer.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}