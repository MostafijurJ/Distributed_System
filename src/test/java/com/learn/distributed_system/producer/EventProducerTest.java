package com.learn.distributed_system.producer;

import com.learn.distributed_system.config.logger.EventLogger;
import com.learn.distributed_system.domain.HelloWorld;
import com.learn.distributed_system.util.EventWrapper;
import com.learn.distributed_system.util.ParentEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {EventProducer.class})
@ExtendWith(SpringExtension.class)
class EventProducerTest {
    @MockBean
    private CommonProducer commonProducer;

    @MockBean
    private EventLogger eventLogger;

    @Autowired
    private EventProducer eventProducer;

    /**
     * Method under test: {@link EventProducer#publishHelloWorldEvent(HelloWorld)}
     */
    @Test
    void testPublishHelloWorldEvent() {
        doNothing().when(commonProducer).sendMessage((String) any(), (EventWrapper<ParentEvent>) any());
        eventProducer.publishHelloWorldEvent(new HelloWorld("Text", "Not all who wander are lost"));
        verify(commonProducer).sendMessage((String) any(), (EventWrapper<ParentEvent>) any());
    }
}

