package com.learn.distributed_system.api;

import com.learn.distributed_system.domain.HelloWorld;
import com.learn.distributed_system.producer.EventProducer;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class DummyApiController {

    @Autowired
    private EventProducer eventProducer;

    @GetMapping
    public void produceMessage() {
        eventProducer.publishHelloWorldEvent(new HelloWorld(generateText(), "Not all who wander are lost"));
    }

    private String generateText(){
       return RandomStringUtils.randomAlphanumeric(10);
    }

}
