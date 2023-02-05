package com.learn.distributed_system.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KafkaTopic {
    public static final String PROCESSING_START = "processing-Start";
    public static final String WORKER_TOPIC = "worker-topic";
}
