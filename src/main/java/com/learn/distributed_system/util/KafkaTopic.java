package com.learn.distributed_system.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaTopic {
    PROCESSING_START("processing-Start"), WORKER_TOPIC("worker-topic");
    private String topicName;
}
