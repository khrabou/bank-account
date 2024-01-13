package com.bankaccount.bankaccount.domain.service;

import com.bankaccount.bankaccount.common.InsufficientFundsException;
import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.*;
import com.bankaccount.bankaccount.domain.ports.in.DepositUseCase;
import com.bankaccount.bankaccount.domain.ports.in.WithdrawUseCase;
import com.bankaccount.bankaccount.domain.ports.out.FindAccountPort;
import com.bankaccount.bankaccount.domain.ports.out.SaveAccountPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OperationsService implements DepositUseCase, WithdrawUseCase {

    public static final String NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID = "No account was found for the given ID";
    public static final String INSUFFICIENT_FUNDS = "Insufficient funds";

    @Autowired
    SaveAccountPort saveAccountPort;

    @Autowired
    FindAccountPort findAccountPort;

    @Override
    public void deposit(long accountId, Amount amount) throws ResourceNotFoundException {
        Account account = findAccountPort.find(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID));
        Balance newBalance = account.balance().add(amount);

        Transaction newTransaction = createTransaction(OperationType.DEPOSIT, amount, newBalance);

        saveUpdatedAccount(accountId, newBalance, account.transactions(), newTransaction);
    }

    @Override
    public void withdraw(long accountId, Amount amount) throws ResourceNotFoundException, InsufficientFundsException {
        Account account = findAccountPort.find(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID));

        Balance newBalance = account.balance().subtract(amount);

        checkSufficientFunds(newBalance);

        Transaction newTransaction = createTransaction(OperationType.WITHDRAW, amount, newBalance);

        saveUpdatedAccount(accountId, newBalance, account.transactions(), newTransaction);
    }

    private void checkSufficientFunds(Balance newBalance) throws InsufficientFundsException {
        if (newBalance.value().signum() == -1) {
            throw new InsufficientFundsException(INSUFFICIENT_FUNDS);
        }
    }

    private Transaction createTransaction(OperationType operationType, Amount amount, Balance newBalance) {
        Operation operation = new Operation(operationType, amount, LocalDateTime.now());
        return new Transaction(operation, newBalance);
    }

    private void saveUpdatedAccount(long accountId, Balance newBalance, List<Transaction> transactions, Transaction newTransaction) {
        List<Transaction> updatedTransactions = new ArrayList<>(transactions);
        updatedTransactions.add(newTransaction);

        saveAccountPort.save(new Account(accountId, newBalance, updatedTransactions));
    }
}
