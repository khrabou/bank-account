package com.bankaccount.bankaccount.domain.service;

import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.Account;
import com.bankaccount.bankaccount.domain.model.Amount;
import com.bankaccount.bankaccount.domain.model.Balance;
import com.bankaccount.bankaccount.domain.ports.in.DepositUseCase;
import com.bankaccount.bankaccount.domain.ports.out.FindAccountPort;
import com.bankaccount.bankaccount.domain.ports.out.SaveAccountPort;
import org.springframework.beans.factory.annotation.Autowired;

public class OperationsService implements DepositUseCase {

    public static final String NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID = "No account was found for the given ID";

    @Autowired
    SaveAccountPort saveAccountPort;

    @Autowired
    FindAccountPort findAccountPort;

    @Override
    public void deposit(long accountId, Amount amount) throws ResourceNotFoundException {
        Account account = findAccountPort.find(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID));
        Balance newBalance = new Balance(account.balance().value().add(amount.value()));
        saveAccountPort.save(new Account(accountId, newBalance));
    }
}
