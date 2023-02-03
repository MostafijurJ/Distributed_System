package com.learn.distributed_system.actor.distributation;


import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.learn.distributed_system.actor.commands.Command;
import com.learn.distributed_system.actor.commands.MasterActorCommand;
import com.learn.distributed_system.actor.commands.SlaveActorCommand;
import com.learn.distributed_system.actor.commands.SlaveResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


@Slf4j
public class MasterActor extends AbstractBehavior<Command> {

    private ActorContext<Command> context;
    private ActorRef<Command> slaveActor;
    private Queue<String> queue = new LinkedList<>();


    public static Behavior<Command> create(ActorRef<Command> monitoringActor, MasterActorCommand request) {

        return Behaviors.setup(context -> new MasterActor(context, monitoringActor, request));
    }

    private MasterActor(ActorContext<Command> context, ActorRef<Command> slaveActor, MasterActorCommand masterActorCommand) {
        super(context);
        this.context = context;
        this.slaveActor = slaveActor;
        executeSlaveActor(masterActorCommand);
    }


    /**
     * Master Actor can be called by anywhere with a chunk of specified tasks
     * Master actor received all the tasks at a time
     * it asks its slave actor to distribute the tasks into the worker actor
     * master actor also monitors slave actor if it is completed his tasks within time
     */
    private void executeSlaveActor(MasterActorCommand command) {
        log.info("Executing slave actor ");
        asker(command.getCommandList());
    }

    private void asker(List<String> tasks) {
        context.ask(
                SlaveResponse.class,
                slaveActor,
                Duration.ofSeconds(30),
                (ActorRef<SlaveResponse> ref) -> new SlaveActorCommand(
                        ref,
                        tasks,
                        true),
                (response, throwable) -> new SlaveResponse(response.flag)
        );
    }
    @Override
    public Receive<Command> createReceive() {

        return newReceiveBuilder()
                .onMessage(SlaveResponse.class, this::onResponse)
                .build();
    }

    private Behavior<Command> onResponse(SlaveResponse response) {

        log.info("Slave Actor Response:  " + response.flag);
        if (response.flag) {
            log.trace("Response Received from slave actor");
        }

        return Behaviors.same();
    }

}
