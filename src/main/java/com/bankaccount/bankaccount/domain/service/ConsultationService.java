package com.bankaccount.bankaccount.domain.service;

import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.Account;
import com.bankaccount.bankaccount.domain.model.Balance;
import com.bankaccount.bankaccount.domain.model.Transaction;
import com.bankaccount.bankaccount.domain.ports.in.ConsultAccountUseCase;
import com.bankaccount.bankaccount.domain.ports.in.ConsultBalanceUseCase;
import com.bankaccount.bankaccount.domain.ports.in.ConsultTransactionsUseCase;
import com.bankaccount.bankaccount.domain.ports.out.FindAccountPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationService implements ConsultBalanceUseCase, ConsultTransactionsUseCase, ConsultAccountUseCase {

    public static final String NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID = "No account was found for the given ID";

    @Autowired
    FindAccountPort findAccountPort;

    @Override
    public Balance balance(long accountId) throws ResourceNotFoundException {
        return findAccountPort.find(accountId).map(Account::balance)
                .orElseThrow(() -> new ResourceNotFoundException(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID));
    }

    @Override
    public List<Transaction> transactions(long accountId) throws ResourceNotFoundException {
        return findAccountPort.find(accountId).map(Account::transactions)
                .orElseThrow(() -> new ResourceNotFoundException(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID));
    }

    @Override
    public Account account(long accountId) throws ResourceNotFoundException {
        return findAccountPort.find(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID));
    }
}
