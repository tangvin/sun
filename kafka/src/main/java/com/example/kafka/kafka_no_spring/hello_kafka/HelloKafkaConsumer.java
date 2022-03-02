package com.example.kafka.kafka_no_spring.hello_kafka;

import com.example.kafka.kafka_no_spring.config.BusiConst;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @Description
 * @Author TY
 * @Date 2021/3/31 21:05
 */
public class HelloKafkaConsumer {

    public static void main(String[] args) {
        //TODO 消费者三个属性必须指定(broker地址清单、key和value的反序列化器)
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);

        //TODO 群组并非必须
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        try {

            //TODO 消费者订阅主题（可以多个）
            consumer.subscribe(Collections.singletonList(BusiConst.HELLO_TOPIC));
            while (true) {

                //TODO 拉取（新版本）
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("topic:%s,分区：%d,偏移量：%d," + "key:%s,value:%s", record.topic(), record.partition(),
                            record.offset(), record.key(), record.value()));
                    //do my work
                    //打包任务投入线程池
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}
