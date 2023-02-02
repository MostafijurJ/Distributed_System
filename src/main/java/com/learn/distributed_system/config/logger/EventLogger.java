package com.learn.distributed_system.config.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventLogger {
    private final Logger errorLogger;
    private final Logger traceLogger;

    public EventLogger() {
        this.errorLogger = LoggerFactory.getLogger("errorLogger");
        this.traceLogger = LoggerFactory.getLogger("traceLogger");
    }

    public void trace(String message) {
        traceLogger.trace(message);
    }

    public void error(String message, Exception ex) {
        errorLogger.error(message, ex);
    }

    public void error(String message) {
        errorLogger.error(message);
    }
}
