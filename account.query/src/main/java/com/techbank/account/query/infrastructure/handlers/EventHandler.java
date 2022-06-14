package com.techbank.account.query.infrastructure.handlers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenEvent;
import com.techbank.account.common.events.FoundsDepositedEvent;
import com.techbank.account.common.events.FoundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenEvent event);
    void on(FoundsDepositedEvent event);
    void on(FoundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
