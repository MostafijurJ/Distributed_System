package com.learn.distributed_system.service;

import com.learn.distributed_system.config.logger.EventLogger;
import com.learn.distributed_system.util.EventWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class BaseService {
    protected EventLogger logger;

    @Autowired
    public void setLogger(EventLogger logger) {
        this.logger = logger;
    }

    public String generateRequestId() {
        return UUID.randomUUID().toString();
    }

    public Timestamp getCurrentTimestamp() {
        return Timestamp.from(Instant.now());
    }

    public EventWrapper<Object> prepareKafkaEvent(String requestId, Timestamp timestamp, Object data) {
        return new EventWrapper<>(requestId, timestamp, data);
    }

}
