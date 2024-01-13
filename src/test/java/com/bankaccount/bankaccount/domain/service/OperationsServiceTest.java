package com.bankaccount.bankaccount.domain.service;

import com.bankaccount.bankaccount.common.InsufficientFundsException;
import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.*;
import com.bankaccount.bankaccount.domain.ports.out.FindAccountPort;
import com.bankaccount.bankaccount.domain.ports.out.SaveAccountPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.bankaccount.bankaccount.domain.service.OperationsService.INSUFFICIENT_FUNDS;
import static com.bankaccount.bankaccount.domain.service.OperationsService.NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(OperationsService.class)
class OperationsServiceTest {

    @Autowired
    OperationsService operationsService;

    @MockBean
    SaveAccountPort saveAccountPort;

    @MockBean
    FindAccountPort findAccountPort;

    @Test
    public void should_deposit_money_into_account() throws ResourceNotFoundException {
        long accountId = 15L;
        Account account = new Account(accountId, new Balance(BigDecimal.ONE));
        Amount amount = new Amount(BigDecimal.ONE);

        when(findAccountPort.find(accountId)).thenReturn(Optional.of(account));

        operationsService.deposit(accountId, amount);

        Transaction expectedTransaction = new Transaction(new Operation(OperationType.DEPOSIT, amount, LocalDateTime.now())
                , new Balance(BigDecimal.TWO));
        Account expectedAccount = new Account(accountId, new Balance(BigDecimal.TWO), List.of(expectedTransaction));
        verify(saveAccountPort, times(1)).save(expectedAccount);
    }

    @Test
    void should_throw_exception_when_account_is_not_found() {
        long accountId = 15L;
        Amount amount = new Amount(BigDecimal.ONE);

        when(findAccountPort.find(accountId)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> operationsService.deposit(accountId, amount));

        then(throwable).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID);

    }

    @Test
    public void should_withdraw_money_from_account() throws ResourceNotFoundException, InsufficientFundsException {
        long accountId = 15L;
        Account account = new Account(accountId, new Balance(BigDecimal.TWO));
        Amount amount = new Amount(BigDecimal.ONE);

        when(findAccountPort.find(accountId)).thenReturn(Optional.of(account));

        operationsService.withdraw(accountId, amount);

        Transaction expectedTransaction = new Transaction(new Operation(OperationType.WITHDRAW, amount, LocalDateTime.now())
                , new Balance(BigDecimal.ONE));
        Account expectedAccount = new Account(accountId, new Balance(BigDecimal.ONE), List.of(expectedTransaction));
        verify(saveAccountPort, times(1)).save(expectedAccount);
    }

    @Test
    void should_throw_exception_when_withdraw_and_account_is_not_found() {
        long accountId = 15L;
        Amount amount = new Amount(BigDecimal.ONE);

        when(findAccountPort.find(accountId)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> operationsService.withdraw(accountId, amount));

        then(throwable).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID);

    }

    @Test
    void should_throw_exception_when_withdraw_and_funds_insufficient() {
        long accountId = 15L;
        Amount amount = new Amount(BigDecimal.TWO);

        Account account = new Account(accountId, new Balance(BigDecimal.ZERO));

        when(findAccountPort.find(accountId)).thenReturn(Optional.of(account));

        Throwable throwable = catchThrowable(() -> operationsService.withdraw(accountId, amount));

        then(throwable).isInstanceOf(InsufficientFundsException.class)
                .hasMessage(INSUFFICIENT_FUNDS);

    }

}