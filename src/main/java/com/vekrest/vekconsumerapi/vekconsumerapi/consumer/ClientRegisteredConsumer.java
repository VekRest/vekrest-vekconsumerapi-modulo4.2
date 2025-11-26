package com.vekrest.vekconsumerapi.vekconsumerapi.consumer;

import com.vekrest.vekconsumerapi.vekconsumerapi.entities.Client;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class ClientRegisteredConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(ClientRegisteredConsumer.class);

    @RetryableTopic(
            autoCreateTopics = "false",
            backoff = @Backoff(
                    delay = 15000,
                    multiplier = 2.0,
                    maxDelay = 54000
            ),
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(
            topics = "${spring.kafka.topic.client.registered}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listener(@Payload ConsumerRecord<String, Client> consumerRecord) {
        LOG.info("VEKCONSUMERAPI -> Um cliente deseja realizar cadastro: {}!", consumerRecord.value().toString());

        if(consumerRecord.headers().lastHeader("TOKEN") != null){
            LOG.info("TOKEN HEADER: {}", new String(consumerRecord.headers().lastHeader("TOKEN").value()));
        }
    }
}