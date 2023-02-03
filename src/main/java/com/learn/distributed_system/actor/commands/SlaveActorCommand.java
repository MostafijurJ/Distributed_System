package com.learn.distributed_system.actor.commands;

import akka.actor.typed.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SlaveActorCommand implements Command {
    private ActorRef<SlaveResponse> parent;
    private List<String> taskList;
    private boolean responseToMaster;
}
