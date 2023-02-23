package com.learn.distributed_system.producer;

import com.learn.distributed_system.config.logger.EventLogger;
import com.learn.distributed_system.domain.HelloWorld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
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
        eventProducer.publishHelloWorldEvent(new HelloWorld("Text", "Not all who wander are lost"));
    }
}

