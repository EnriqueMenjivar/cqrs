package com.techbank.account.query.infrastructure.handlers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenEvent;
import com.techbank.account.common.events.FoundsDepositedEvent;
import com.techbank.account.common.events.FoundsWithdrawnEvent;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountEventHandler implements EventHandler{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void on(AccountOpenEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();

        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FoundsDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if(bankAccount.isEmpty())
            return;

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FoundsWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if(bankAccount.isEmpty())
            return;

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
