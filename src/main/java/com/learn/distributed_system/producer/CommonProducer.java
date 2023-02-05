package com.learn.distributed_system.producer;

import com.learn.distributed_system.service.BaseService;
import com.learn.distributed_system.util.EventWrapper;
import com.learn.distributed_system.util.ParentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CommonProducer extends BaseService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @Async
    public void sendMessage(String topicName, EventWrapper<? super ParentEvent> eventWrapper) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, eventWrapper);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.trace("Sent message=[" + eventWrapper + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                logger.trace("Unable to send message=[" + eventWrapper + "] due to : " + ex.getMessage());
            }
        });
    }
}
