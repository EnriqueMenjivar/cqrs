package com.techbank.account.cmd.api.commands;

import com.techbank.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFoundsCommand extends BaseCommand {
    private Double amount;
}
