package com.kata.bankaccount.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BalanceTest {

    @Test
    void should_create_balance_ten_value(){
        Balance balance = new Balance(BigDecimal.TEN);

        Assertions.assertEquals(BigDecimal.TEN,balance.value());
    }

    @Test
    void should_add_ten_to_balance_with_zero_value(){
        Balance balance = new Balance(BigDecimal.ZERO);

        Balance newBalance = balance.add(new Amount(BigDecimal.TEN));

        Assertions.assertEquals(BigDecimal.TEN,newBalance.value());
    }

}

