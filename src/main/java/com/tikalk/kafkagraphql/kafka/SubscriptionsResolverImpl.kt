package com.tikalk.kafkagraphql.kafka

import com.tikalk.kafkagraphql.dto.Message
import com.tikalk.kafkagraphql.graphql.GraphqlEvents
import graphql.schema.DataFetchingEnvironment
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions

@Component
class SubscriptionsResolverImpl(
    val receiverOptions: ReceiverOptions<String, Message>                 // Receiver configuration. Injected by Spring
) : SubscriptionsResolver {

    private val logger = LoggerFactory.getLogger("SubscriptionsResolverImpl")

    private val kafkaReceiver: Flux<GraphqlEvents> by lazy {         // Use lazy to delay KafkaReceiver initialization
        val a = KafkaReceiver.create(receiverOptions).receive()                     // Create Kafka reactive receiver
        val b = a.map { GraphqlEvents.fromEvent(it.value()) }             // Map from Kafka event to Graphql Event
        val c = b.doFinally { logger.info("Closing with signal: ${it.name}") }   // Log message on stream closure
        val d = c.publish()                                                      // Get a ConnectableFlux. Turns stream to hot
        val e = d.autoConnect()                                                  // Connect to upstream on first subscription
        return e
    }

    override fun event(env: DataFetchingEnvironment): Publisher<GraphqlEvents> {
        logger.info("GraphQL 'event' subscription called")                  // Log message on each new subscription
        return kafkaReceiver                                                // Returns kafkaReceiver for GraphQL to subscribe to it
    }

    fun lazy1(function: () -> Flux<GraphqlEvents>): GraphqlEvents {
        val a = KafkaReceiver.create(receiverOptions).receive()                     // Create Kafka reactive receiver
        val b = a.map { GraphqlEvents.fromEvent(it.value()) }             // Map from Kafka event to Graphql Event
        val c = b.doFinally { logger.info("Closing with signal: ${it.name}") }   // Log message on stream closure
        val d = c.publish()                                                      // Get a ConnectableFlux. Turns stream to hot
        val e = d.autoConnect()                                                  // Connect to upstream on first subscription
        return e.blockFirst()!!
    }
}

interface SubscriptionsResolver {

    fun event(env: DataFetchingEnvironment): Publisher<GraphqlEvents>
    fun lazy(function: () -> Flux<GraphqlEvents>): GraphqlEvents
}
