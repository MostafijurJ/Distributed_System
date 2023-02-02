package com.learn.distributed_system.util;

import java.io.Serializable;
import java.sql.Timestamp;

public class EventWrapper<T> implements Serializable {
    private String requestId;
    private Timestamp timestamp;
    private T data;

    public EventWrapper() {
    }

    public EventWrapper(String requestId, Timestamp timestamp, T data) {
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.data = data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}