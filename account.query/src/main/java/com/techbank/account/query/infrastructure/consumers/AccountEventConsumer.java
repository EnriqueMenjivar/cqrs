package com.techbank.account.query.infrastructure.consumers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenEvent;
import com.techbank.account.common.events.FoundsDepositedEvent;
import com.techbank.account.common.events.FoundsWithdrawnEvent;
import com.techbank.account.query.infrastructure.handlers.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumer{

    @Autowired
    private EventHandler eventHandler;

    @KafkaListener(topics = "AccountOpenEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountOpenEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FoundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FoundsDepositedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FoundsWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FoundsWithdrawnEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountClosedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }
}
