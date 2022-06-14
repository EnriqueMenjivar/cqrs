package com.techbank.account.cmd.api.commands;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
    void handle(DepositFoundsCommand command);
    void handle(WithdrawFoundsCommand command);
    void handle(CloseAccountCommand command);
    void handle(RestoreDbCommand command);
}
