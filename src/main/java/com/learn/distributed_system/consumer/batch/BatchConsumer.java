package com.learn.distributed_system.consumer.batch;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import com.learn.distributed_system.actor.commands.Command;
import com.learn.distributed_system.actor.commands.WorkerActorCommand;
import com.learn.distributed_system.actor.parallal_task.WorkerActor;
import com.learn.distributed_system.service.BaseService;
import com.learn.distributed_system.util.KafkaTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class BatchConsumer extends BaseService {


    @KafkaListener(topics = KafkaTopic.WORKER_TOPIC,  groupId ="${kafka.consumer.groups.secondary}", containerFactory = "secondaryKafkaListenerContainerFactory")
    public void EFTNEventConsumer(@Payload List<ConsumerRecord<String,String>> records){
        logger.trace("Working started on ");
        List<String> taskList = getTaskList(records);


        for (String task : taskList) {
            ActorRef<Command> workerActor = ActorSystem.create(WorkerActor.create(), "WorkerActor-" + UUID.randomUUID());
            workerActor.tell(new WorkerActorCommand(task));

            logger.trace("worker actor invoked");
        }

    }


    private List<String> getTaskList(List<ConsumerRecord<String,String>> records){
        List<String> taskList = new ArrayList<>();
        for(ConsumerRecord<String,String> record : records){
            taskList.add(record.value());
        }
        return taskList;
    }

}
