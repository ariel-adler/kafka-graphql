package com.tikalk.kafkagraphql.config;

import graphql.Scalars;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;

@Configuration
public class GraphQlConfig {
    @Bean
    GraphQLSchema schema() {
        DataFetcher<CompletableFuture<String>> test =
                env ->
                        CompletableFuture.supplyAsync(
                                () -> {
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                    return "response";
                                });
        return GraphQLSchema.newSchema()
                .query(
                        GraphQLObjectType.newObject()
                                .name("query")
                                .field(field -> field.name("test").type(Scalars.GraphQLString))
                                .build())
                .codeRegistry(
                        GraphQLCodeRegistry.newCodeRegistry()
                                .dataFetcher(FieldCoordinates.coordinates("query", "test"), test)
                                .build())
                .build();
    }
}
