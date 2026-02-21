package com.bankaccount.bankaccount.application.rest;

import com.bankaccount.bankaccount.common.InsufficientFundsException;
import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.Account;
import com.bankaccount.bankaccount.domain.model.Amount;
import com.bankaccount.bankaccount.domain.ports.in.ConsultAccountUseCase;
import com.bankaccount.bankaccount.domain.ports.in.DepositUseCase;
import com.bankaccount.bankaccount.domain.ports.in.WithdrawUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    ConsultAccountUseCase consultAccountUseCase;
    @Autowired
    DepositUseCase depositUseCase;
    @Autowired
    WithdrawUseCase withdrawUseCase;

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable long accountId) throws ResourceNotFoundException {
        return consultAccountUseCase.account(accountId);
    }

    @PostMapping("/{accountId}/deposit/{amount}")
    public void deposit(@PathVariable long accountId, @PathVariable BigDecimal amount) throws ResourceNotFoundException {
        depositUseCase.deposit(accountId, new Amount(amount));
    }

    @PostMapping("/{accountId}/withdraw/{amount}")
    public void withdraw(@PathVariable long accountId, @PathVariable BigDecimal amount) throws ResourceNotFoundException, InsufficientFundsException {
        withdrawUseCase.withdraw(accountId, new Amount(amount));
    }
}
