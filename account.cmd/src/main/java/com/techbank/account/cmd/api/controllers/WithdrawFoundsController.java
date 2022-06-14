package com.techbank.account.cmd.api.controllers;

import com.techbank.account.cmd.api.commands.DepositFoundsCommand;
import com.techbank.account.cmd.api.commands.WithdrawFoundsCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.exception.AggregateNotFoundException;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/deposit-founds")
@Slf4j
public class WithdrawFoundsController {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> withdrawFounds(@PathVariable String id, @RequestBody WithdrawFoundsCommand command) {

        try{
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Withdraw founds request completed successfully"), HttpStatus.OK);
        }catch (IllegalStateException | AggregateNotFoundException e) {
            log.warn("Client made a bad request - {0}.", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            log.warn("Error while processing request to deposit a bank account for id - {0}.", id);
            return new ResponseEntity<>(new OpenAccountResponse("Error while processing request to withdraw founds from bank account with id - {0}."),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
