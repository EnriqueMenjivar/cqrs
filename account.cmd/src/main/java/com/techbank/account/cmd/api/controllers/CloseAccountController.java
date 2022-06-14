package com.techbank.account.cmd.api.controllers;

import com.techbank.account.cmd.api.commands.CloseAccountCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.exception.AggregateNotFoundException;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/close-bank-account")
@Slf4j
public class CloseAccountController {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable String id) {
        try {
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("Bank account closure request successfully completed"), HttpStatus.OK);
        }catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a bad request - {0}.", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            log.warn("Error while processing request to close a bank account for id - {0}.", id);
            return new ResponseEntity<>(new OpenAccountResponse("Error while processing request to close account from bank account with id - {0}."),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
