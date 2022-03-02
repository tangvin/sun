package com.example.kafka.kafka_no_spring.commit;

import com.example.kafka.kafka_no_spring.config.BusiConst;
import com.example.kafka.kafka_no_spring.config.KafkaConst;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import sun.reflect.FieldInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

/**
 * @Description 特定提交
 * @Author TY
 * @Date 2021/4/5 10:27
 */
public class CommitSpecial {
    public static void main(String[] args) {
        /*消息消费者*/
        Properties properties = KafkaConst.consumerConfig(
                "CommitSpecial",
                StringDeserializer.class,
                StringDeserializer.class);
        /*取消自动提交*/
        properties.put("enable.auto.commit", false);
        KafkaConsumer<String, String> consumer
                = new KafkaConsumer<String, String>(properties);

        HashMap<TopicPartition, OffsetAndMetadata> currOffsets = new HashMap<>();
        int count = 0;
        try {

            consumer.subscribe(Collections.singletonList(BusiConst.CONSUMER_COMMIT_TOPIC));
            while (true) {

                ConsumerRecords<String, String> records = consumer.poll(500);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "主题：%s，分区：%d，偏移量：%d，key：%s，value：%s",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value()
                    ));
                    currOffsets.put(
                            new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1, "no metadata")
                    );
                    if (count % 11 == 0) {
                        consumer.commitAsync(currOffsets, null);
                    }
                    count++;
                }
            }
        } finally {
            consumer.close();
        }


    }
}
