package com.example.kafka.kafka_no_spring.concurrent;

import com.example.kafka.kafka_no_spring.config.BusiConst;
import com.example.kafka.kafka_no_spring.config.KafkaConst;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 多线程下正确的使用消费者，需要记住，一个线程一个消费者
 * @Author TY
 * @Date 2021/4/9 20:29
 */
public class KafkaConConsumer {


    private static ExecutorService executorService = Executors.newFixedThreadPool(BusiConst.CONCURRENT_PARTITIONS_COUNT);

    private static class ConsumerWorker implements Runnable {


        private KafkaConsumer<String, String> consumer;


        public ConsumerWorker(Map<String, Object> config, String topic) {

            Properties properties = new Properties();
            properties.putAll(config);
            this.consumer = new KafkaConsumer<String, String>(properties);
            consumer.subscribe(Collections.singletonList(topic));
        }

        @Override
        public void run() {

            final String id = Thread.currentThread().getId() + "_" + System.identityHashCode(consumer);
            try {

                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println(id + "|" + String.format(
                                "主题：%s，分区：%d，偏移量：%d，" +
                                        "key：%s，value：%s",
                                record.topic(), record.partition(),
                                record.offset(), record.key(), record.value()));
                        //do our work
                    }
                }
            } finally {
                consumer.close();
            }
        }
    }

    public static void main(String[] args) {
        /*消费配置的实例*/

        Map<String, Object> config
                = KafkaConst.consumerConfigMap("concurrent",
                StringDeserializer.class,
                StringDeserializer.class);

        for (int i = 0; i < BusiConst.CONCURRENT_PARTITIONS_COUNT; i++) {
            executorService.submit(new ConsumerWorker(config, BusiConst.CONCURRENT_USER_INFO_TOPIC));
        }
    }
}
