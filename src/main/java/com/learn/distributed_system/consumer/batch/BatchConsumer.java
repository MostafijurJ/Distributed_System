package com.learn.distributed_system.consumer.batch;

import com.learn.distributed_system.service.BaseService;
import com.learn.distributed_system.util.KafkaTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BatchConsumer extends BaseService {


    @KafkaListener(topics = KafkaTopic.WORKER_TOPIC,  groupId ="${kafka.consumer.groups.secondary}", containerFactory = "secondaryKafkaListenerContainerFactory")
    public void EFTNEventConsumer(@Payload List<ConsumerRecord<String,String>> records){

        logger.trace("[LIGHT-EFTN-Transaction Request] : " + records.size() + "\tTime : "+ LocalDateTime.now());
    }
}
