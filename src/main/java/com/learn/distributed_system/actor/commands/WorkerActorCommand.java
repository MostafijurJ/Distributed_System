package com.learn.distributed_system.actor.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WorkerActorCommand implements Command {
    private String taskName;
}
