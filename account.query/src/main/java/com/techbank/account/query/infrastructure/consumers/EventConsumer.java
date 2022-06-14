package com.techbank.account.query.infrastructure.consumers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenEvent;
import com.techbank.account.common.events.FoundsDepositedEvent;
import com.techbank.account.common.events.FoundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenEvent event, Acknowledgment ack);
    void consume(@Payload FoundsDepositedEvent event, Acknowledgment ack);
    void consume(@Payload FoundsWithdrawnEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
