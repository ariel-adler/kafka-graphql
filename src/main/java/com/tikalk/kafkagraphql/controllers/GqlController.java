package com.tikalk.kafkagraphql.controllers;

import com.tikalk.kafkagraphql.dto.Message;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.List;

@Controller
public class GqlController {
    @SubscriptionMapping
    public Flux<Message> gotMessage() {
        var msg = new Message();
        msg.setText("Test message");
        msg.setTimestamp(Instant.now());

        return Flux.fromIterable(List.of(msg));
    }

}
