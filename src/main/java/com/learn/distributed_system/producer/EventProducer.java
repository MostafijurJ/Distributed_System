package com.learn.distributed_system.producer;

import com.learn.distributed_system.domain.HelloWorld;
import com.learn.distributed_system.service.BaseService;
import com.learn.distributed_system.util.EventWrapper;
import com.learn.distributed_system.util.KafkaTopic;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventProducer extends BaseService {
    private final CommonProducer commonProducer;
    public void publishHelloWorldEvent(HelloWorld   event) {
        EventWrapper<Object> objectEventWrapper = prepareKafkaEvent(generateRequestId(), getCurrentTimestamp(), event);
        commonProducer.sendMessage(KafkaTopic.PROCESSING_START, objectEventWrapper);
        logger.trace("Kafka topic started successfully published");
    }

}
