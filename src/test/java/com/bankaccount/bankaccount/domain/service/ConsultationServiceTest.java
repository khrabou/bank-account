package com.bankaccount.bankaccount.domain.service;

import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.*;
import com.bankaccount.bankaccount.domain.ports.out.FindAccountPort;
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

import static com.bankaccount.bankaccount.domain.service.ConsultationService.NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({ConsultationService.class})
public class ConsultationServiceTest {

    @Autowired
    ConsultationService consultationService;

    @MockBean
    FindAccountPort findAccountPort;

    @Test
    void should_return_balance() throws ResourceNotFoundException {
        long accountId = 15L;
        Balance expectedBalance = new Balance(BigDecimal.TEN);
        Account account = new Account(accountId, expectedBalance);

        when(findAccountPort.find(accountId)).thenReturn(Optional.of(account));

        Balance balance = consultationService.balance(accountId);

        assertThat(balance).isEqualTo(expectedBalance);
    }

    @Test
    void should_throw_exception_when_account_is_not_found() {
        long accountId = 15L;

        when(findAccountPort.find(accountId)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> consultationService.balance(accountId));

        then(throwable).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID);

    }

    @Test
    void should_return_one_transaction() throws ResourceNotFoundException {
        long accountId = 15L;
        Transaction expectedTransaction = new Transaction(new Operation(OperationType.DEPOSIT, new Amount(BigDecimal.TEN), LocalDateTime.now())
                , new Balance(BigDecimal.TEN));

        Account account = new Account(accountId, new Balance(BigDecimal.TEN), List.of(expectedTransaction));

        when(findAccountPort.find(accountId)).thenReturn(Optional.of(account));

        List<Transaction> transactions = consultationService.transactions(accountId);

        assertThat(transactions).containsExactly(expectedTransaction);
    }

    @Test
    void should_throw_exception_when_consulting_transactions_and_account_is_not_found() {
        long accountId = 15L;

        when(findAccountPort.find(accountId)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> consultationService.transactions(accountId));

        then(throwable).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID);

    }
}
