package com.authservice.auth.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MovieConsumer {


    @KafkaListener(topics = "movie-topic",groupId = "auth-groupId")
    public void processMessage(ConsumerRecord<Long,String> record){
        System.out.println("Message received: " + record.value());
    }


}
