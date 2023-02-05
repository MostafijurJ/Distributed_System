package com.learn.distributed_system.config.kafka;

import com.learn.distributed_system.util.KafkaTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.partition-size}")
    private int topicPartitionSize;

    @Value("${kafka.replication-factor}")
    private int topicReplicationSize;

    @Bean
    public NewTopic startTopic(){
        return TopicBuilder.name(KafkaTopic.PROCESSING_START)
                .partitions(topicPartitionSize)
                .replicas(topicReplicationSize)
                .build();
    }

    @Bean
    public NewTopic workerTopic(){
        return TopicBuilder.name(KafkaTopic.WORKER_TOPIC)
                .partitions(topicPartitionSize)
                .replicas(topicReplicationSize)
                .build();
    }

}
