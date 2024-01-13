package com.bankaccount.bankaccount.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.bankaccount.bankaccount.domain.model.Amount.AMOUNT_MUST_NOT_BE_NEGATIVE;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

public class AmountTest {

    @Test
    void should_throw_exception_when_amount_is_negative() {
        BigDecimal amountValue = new BigDecimal(-1);

        Throwable throwable = catchThrowable(() -> new Amount(amountValue));

        then(throwable).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(AMOUNT_MUST_NOT_BE_NEGATIVE);

    }
}
