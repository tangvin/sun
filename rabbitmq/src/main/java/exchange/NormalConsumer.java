package exchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 普通消费者
 */
public class NormalConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接、连接到RabbitMQ
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置下连接工厂的连接地址(使用默认端口5672)
        connectionFactory.setHost("localhost");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();

        //在信道中设置交换器
        channel.exchangeDeclare(DirectProductor.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //申明队列
        String queueName = "queue-king";
        channel.queueDeclare(queueName, false, false, false, null);
        //绑定（将对列<queue-king>与交换机 通过 路由键 绑定 <king>）
        String routeKey = "king";
        channel.queueBind(queueName, DirectProductor.EXCHANGE_NAME, routeKey);
        System.out.println("waiting for message ......");

        //声明一个消费者
        final DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received[" + envelope.getRoutingKey() + "]" + msg);
            }
        };
        //消费值正式开始在指定的对列消费
        channel.basicConsume(queueName, true, defaultConsumer);

    }
}
