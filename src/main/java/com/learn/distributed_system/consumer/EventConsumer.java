package com.learn.distributed_system.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.distributed_system.domain.HelloWorld;
import com.learn.distributed_system.service.BaseService;
import com.learn.distributed_system.util.EventWrapper;
import com.learn.distributed_system.util.KafkaTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventConsumer extends BaseService {
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = KafkaTopic.PROCESSING_START, groupId = "${kafka.consumer.groups.primary}")
    public void consumeEvent(@Payload String event) {
        try {
            EventWrapper<HelloWorld> eventWrapper = objectMapper.readValue(event, new TypeReference<>() {
            });

            logger.trace(eventWrapper.getData().getMessage());

            System.out.println(objectMapper.writeValueAsString(event));
        } catch (Exception e) {
            logger.error("Failed to consume event");
        }
    }
}
