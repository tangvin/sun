package com.example.kafka.kafka_no_spring.commit;

import com.example.kafka.kafka_no_spring.config.BusiConst;
import com.example.kafka.kafka_no_spring.config.KafkaConst;
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
 * @Date 2021/4/5 10:13
 * 类说明：手动提交当偏移量，生产者使用ProducerCommit
 */
public class CommitSync {

    public static void main(String[] args) {
        /*消息消费者*/
        Properties properties = KafkaConst.consumerConfig("CommitSync",
                StringDeserializer.class,
                StringDeserializer.class);
        /*取消自动提交*/
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer
                = new KafkaConsumer<String, String>(properties);
        try {
            consumer.subscribe(Collections.singletonList(
                    BusiConst.CONSUMER_COMMIT_TOPIC));
            while (true) {
                ConsumerRecords<String, String> records
                        = consumer.poll(500);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format(
                            "主题：%s，分区：%d，偏移量：%d，key：%s，value：%s",
                            record.topic(), record.partition(), record.offset(),
                            record.key(), record.value()));
                    //do our work

                }
                //开始事务
                //读业务写数据库-
                //偏移量写入数据库
                //提交
                consumer.commitSync();
            }
        } finally {
            consumer.close();
        }
    }
}
