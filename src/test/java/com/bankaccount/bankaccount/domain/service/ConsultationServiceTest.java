package com.bankaccount.bankaccount.domain.service;

import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.Account;
import com.bankaccount.bankaccount.domain.model.Balance;
import com.bankaccount.bankaccount.domain.ports.out.FindAccountPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
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

        Balance balance = consultationService.consultBalance(accountId);

        assertThat(balance).isEqualTo(expectedBalance);
    }

    @Test
    void should_throw_exception_when_account_is_not_found() {
        long accountId = 15L;

        when(findAccountPort.find(accountId)).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> consultationService.consultBalance(accountId));

        then(throwable).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID);

    }
}
