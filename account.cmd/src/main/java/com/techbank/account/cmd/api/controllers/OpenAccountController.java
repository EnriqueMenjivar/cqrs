package com.techbank.account.cmd.api.controllers;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/open-bank-account")
@Slf4j
public class OpenAccountController {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        String id = UUID.randomUUID().toString();
        command.setId(id);

        try{
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Creation request completed successfully"), HttpStatus.CREATED);
        }catch (IllegalStateException e) {
            log.warn("Client made a bad request - {0}.", e.getMessage());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.warn("Error while processing request to open a new bank account for id - {0}.", id);
            return new ResponseEntity<>(new OpenAccountResponse("Error while processing request to open a new bank account for id - {0}."),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
