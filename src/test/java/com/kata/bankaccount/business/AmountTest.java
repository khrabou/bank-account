package com.kata.bankaccount.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AmountTest {

    @Test
    void should_throw_exception_when_amount_value_is_negative(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Amount(new BigDecimal(-10));
        });
    }

}

