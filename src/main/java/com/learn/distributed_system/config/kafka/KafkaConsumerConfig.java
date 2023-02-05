package com.learn.distributed_system.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String server;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String offset;

    @Value("${kafka.consumer.groups.primary}")
    private String consumerGroupIdPrimary;

    @Value("${kafka.consumer.groups.secondary}")
    private String consumerGroupIdSecondary;

    @Value("${kafka.polling-size}")
    private String kafkaPollingSizeSecondary;


    @Bean
    public ConsumerFactory<String, Object> consumerFactory(){

        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupIdPrimary);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset);

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> primaryKafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }


    /**
     * Multple consumer configuration implementation for huge amounts of event polling
     **/
    @Bean
    public ConsumerFactory<String, String> secondaryConsumerFactory(){

        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupIdSecondary);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "524288000");
        properties.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "3000");
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaPollingSizeSecondary);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(ConsumerConfig.RECEIVE_BUFFER_CONFIG,33554432); //default value

        return new DefaultKafkaConsumerFactory<>(properties);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> gatewayKafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(secondaryConsumerFactory());
        factory.setBatchListener(true);
        return factory;
    }
}
