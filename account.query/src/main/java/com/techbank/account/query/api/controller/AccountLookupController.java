package com.techbank.account.query.api.controller;

import com.techbank.account.query.api.dto.AccountLookupResponse;
import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.api.queries.FindAccountByHolderQuery;
import com.techbank.account.query.api.queries.FindAccountByIdQuery;
import com.techbank.account.query.api.queries.FindAccountWithBalanceQuery;
import com.techbank.account.query.api.queries.FindAllAccountsQuery;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/api/v1/back-account-lookup")
@Slf4j
public class AccountLookupController {

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());

            if(accounts == null || accounts.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank account(s)", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var message = "Fail to complete get all accounts request";
            log.warn(message);

            return new ResponseEntity<>(new AccountLookupResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable String id) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));

            if(accounts == null || accounts.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank account")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var message = "Fail to complete get account request";
            log.warn(message);

            return new ResponseEntity<>(new AccountLookupResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/holder/{holder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable String holder) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(holder));

            if(accounts == null || accounts.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message("Successfully returned bank account")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var message = "Fail to complete get account request";
            log.warn(message);

            return new ResponseEntity<>(new AccountLookupResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/balance/{equality}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountByBalance(@PathVariable EqualityType equality, @PathVariable double balance) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(equality, balance));

            if(accounts == null || accounts.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} bank account(s)", accounts.size()))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var message = "Fail to complete get accounts with balance request";
            log.warn(message);

            return new ResponseEntity<>(new AccountLookupResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
