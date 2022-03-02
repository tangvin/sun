package exchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class DirectProductor {
    public static final String EXCHANGE_NAME = "direct_log";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接，连接到RabbitMQ
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接地址
        connectionFactory.setHost("localhost");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //在信道中设置交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明队列（放到消费者中做）

        //声明路由键。消息体
        String routeKeys[] = {"jack", "king", "mark"};
        for (int i = 0; i < 6; i++) {
            String routeKey = routeKeys[i % 3];
            String msg = "hello , rabbitmq " + (i + 1);
            //发布消息
            channel.basicPublish(EXCHANGE_NAME, routeKey, null, msg.getBytes());
            System.out.println("sent " + routeKey + ": " + msg);
        }
        channel.close();
        connection.close();

    }


}
