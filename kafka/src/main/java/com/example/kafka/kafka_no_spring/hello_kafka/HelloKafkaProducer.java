package com.example.kafka.kafka_no_spring.hello_kafka;

import com.example.kafka.kafka_no_spring.config.BusiConst;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @Description
 * @Author TY
 * @Date 2021/3/31 21:13
 */
public class HelloKafkaProducer {
    public static void main(String[] args) {
        //TODO 生产者三个属性必须指定(broker地址清单、key和value的序列化器)
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        try {
            ProducerRecord record = null;

            for (int i = 0; i < 4; i++) {
                record = new ProducerRecord<String, String>(BusiConst.HELLO_TOPIC, null, "lison");
                //发送并发忘记（重试会有）
                producer.send(record);
                System.out.println(i + "，message is sented!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }

    }
}
