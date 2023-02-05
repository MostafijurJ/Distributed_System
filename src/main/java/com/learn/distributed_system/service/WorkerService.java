package com.learn.distributed_system.service;

import com.learn.distributed_system.producer.CommonProducer;
import com.learn.distributed_system.util.EventWrapper;
import com.learn.distributed_system.util.KafkaTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerService extends BaseService{

    @Autowired
    private CommonProducer commonProducer;

    public void eventPublisher(String event){
        EventWrapper<Object> eventWrapper = prepareKafkaEvent(event, getCurrentTimestamp(), generateRequestId());
        commonProducer.sendMessage(KafkaTopic.WORKER_TOPIC, eventWrapper);
    }

    public void startWorking(String taskName){
        logger.trace("worker started from here with task name "+taskName);
    }


}
