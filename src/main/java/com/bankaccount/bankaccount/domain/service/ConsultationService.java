package com.bankaccount.bankaccount.domain.service;

import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.Account;
import com.bankaccount.bankaccount.domain.model.Balance;
import com.bankaccount.bankaccount.domain.ports.in.ConsultBalanceUseCase;
import com.bankaccount.bankaccount.domain.ports.out.FindAccountPort;
import org.springframework.beans.factory.annotation.Autowired;

public class ConsultationService implements ConsultBalanceUseCase {

    public static final String NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID = "No account was found for the given ID";

    @Autowired
    FindAccountPort findAccountPort;

    @Override
    public Balance consultBalance(long accountId) throws ResourceNotFoundException {
        return findAccountPort.find(accountId).map(Account::balance)
                .orElseThrow(() -> new ResourceNotFoundException(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID));
    }
}
