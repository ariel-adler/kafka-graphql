package com.tikalk.kafkagraphql.graphql;

import com.tikalk.kafkagraphql.dto.Message;
import com.tikalk.kafkagraphql.kafka.SubscriptionsResolverImpl;
import kotlin.reflect.KProperty;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

import java.util.List;

@Getter
public class GraphqlEvents {
    private final String text;

    public GraphqlEvents(String text) {
        this.text = text;
    }

    @NotNull
    public Flux<GraphqlEvents> getValue(@NotNull SubscriptionsResolverImpl subscriptionsResolverImpl, @NotNull KProperty<?> property) {
        return Flux.fromIterable(List.of(new GraphqlEvents("abc")));
    }

    public static GraphqlEvents fromEvent(Message message) {
        return new GraphqlEvents(message.getText());
    }

}
