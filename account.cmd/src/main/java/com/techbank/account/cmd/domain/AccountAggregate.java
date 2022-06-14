package com.techbank.account.cmd.domain;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenEvent;
import com.techbank.account.common.events.FoundsDepositedEvent;
import com.techbank.account.common.events.FoundsWithdrawnEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private Double balance;

    public double getBalance() {
        return this.balance;
    }
    public boolean getActive() {
        return this.active;
    }

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFounds(double amount) {
        if(!this.active)
            throw new IllegalStateException("Founds cannot be deposited into a closed account!");

        if(amount <= 0)
            throw new IllegalStateException("The deposited amount must ve greater that 0!");

        raiseEvent(FoundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FoundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFounds(double amount) {
        if(!this.active)
            throw new IllegalStateException("Founds cannot be withdrawn into a closed account!");

        raiseEvent(FoundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FoundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void closeAccount() {
        if(!this.active)
            throw new IllegalStateException("Bank account has already been closed!");

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }

}
