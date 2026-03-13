package com.nautik.api.controller.messaging;


import com.google.common.util.concurrent.ListenableFuture;
import com.nautik.api.dto.messaging.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1/messaging")
@RequiredArgsConstructor
public class EmailController {


    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

    @Value(value = "${message.topic.name}")
    private String topicName;

    @PostMapping
    public MessageDto   sendMessage (@RequestBody MessageDto message) throws ExecutionException, InterruptedException {
        CompletableFuture<SendResult<String, MessageDto >> future = kafkaTemplate.send(topicName, message);
        return future.get().getProducerRecord().value();
    }


}
