package com.learn.distributed_system.actor.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MasterActorCommand {
    private List<String> commandList;
}
