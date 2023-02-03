package com.learn.distributed_system.actor.commands;

public class SlaveResponse implements Command {

    public final boolean flag;

    public SlaveResponse(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
}
