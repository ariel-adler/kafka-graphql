package com.tikalk.kafkagraphql;

import com.tikalk.kafkagraphql.dto.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.kafka.receiver.ReceiverOptions;

@SpringBootApplication
public class KafkaGraphqlApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkaGraphqlApplication.class, args);
	}

	@Bean
	ReceiverOptions<String, Message>  receiverOptions(){
		return ReceiverOptions.create();
	}
}
