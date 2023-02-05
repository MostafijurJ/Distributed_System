package com.learn.distributed_system.actor.distributation;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.learn.distributed_system.actor.commands.Command;
import com.learn.distributed_system.actor.commands.WorkerActorCommand;
import com.learn.distributed_system.service.WorkerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributorActor extends AbstractBehavior<Command> {

    private final WorkerService workerService;
    private DistributorActor(ActorContext<Command> context) {
        super(context);
         this.workerService = new WorkerService();
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(DistributorActor::new);
    }

    @Override
    public Receive<Command> createReceive() {

        return newReceiveBuilder()
                .onMessage(WorkerActorCommand.class, this::startWork)
                .onSignal(PostStop.class, signal -> onPostStop())
                .build();
    }

    private Behavior<Command> startWork(WorkerActorCommand command){
        log.info("DistributorActor started with task named with " + command.getTaskName());
        workerService.eventPublisher(command.getTaskName());
        return Behaviors.stopped();
    }

    private Behavior<Command> onPostStop() {
        return this;
    }
}
