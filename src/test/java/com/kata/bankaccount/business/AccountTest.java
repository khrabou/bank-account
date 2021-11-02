package com.kata.bankaccount.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AccountTest {

    @Test
    void create_account_zero_balance(){
        Account account = new Account(new Balance(BigDecimal.ZERO));
        Assertions.assertEquals(new Balance(BigDecimal.ZERO),account.balance());
    }

    @Test
    void should_create_account_3000_balance(){
        Account account = new Account(new Balance(BigDecimal.TEN));
        Assertions.assertEquals(new Balance(BigDecimal.TEN),account.balance());
    }

}

