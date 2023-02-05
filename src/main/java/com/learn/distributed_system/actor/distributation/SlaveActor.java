package com.learn.distributed_system.actor.distributation;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.learn.distributed_system.actor.commands.Command;
import com.learn.distributed_system.actor.commands.SlaveActorCommand;
import com.learn.distributed_system.actor.commands.SlaveResponse;
import com.learn.distributed_system.actor.commands.WorkerActorCommand;

import java.util.UUID;

public class SlaveActor extends AbstractBehavior<Command> {

    public static Behavior<Command> create() {
        return Behaviors.setup(SlaveActor::new);
    }

    private SlaveActor(ActorContext<Command> context) {
        super(context);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(SlaveActorCommand.class, this::onMessageReceived)
                .build();
    }

    private Behavior<Command> onMessageReceived(SlaveActorCommand command) {

        try{
            for(String singleTask: command.getTaskList()){

                ActorRef<Command> validationProcess = getContext().spawn(DistributorActor.create(), "worker-" + UUID.randomUUID());
                validationProcess.tell(new WorkerActorCommand(singleTask));
            }

            command.getParent().tell(new SlaveResponse(true));
        }
        catch (Exception e){
            command.getParent().tell(new SlaveResponse(false));
        }

        return Behaviors.same();
    }

}
