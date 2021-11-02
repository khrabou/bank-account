package com.kata.bankaccount.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AccountTest {

    @Test
    void should_create_account_zero_balance(){
        Account account = new Account(new Balance(BigDecimal.ZERO));

        Assertions.assertEquals(new Balance(BigDecimal.ZERO),account.balance());
    }

    @Test
    void should_create_account_ten_balance(){
        Account account = new Account(new Balance(BigDecimal.TEN));

        Assertions.assertEquals(new Balance(BigDecimal.TEN),account.balance());
    }

    @Test
    void should_deposit_amount_10_in_account_with_0_balance(){
        Account account = new Account(new Balance(BigDecimal.ZERO));
        Amount amount = new Amount(BigDecimal.TEN);

        account.deposit(amount);

        Assertions.assertEquals(new Balance(BigDecimal.TEN),account.balance());
    }

    @Test
    void should_withdrawal_amount_10_from_account_with_0_balance(){
        Account account = new Account(new Balance(BigDecimal.ZERO));
        Amount amount = new Amount(BigDecimal.TEN);

        account.withdrawal(amount);

        Assertions.assertEquals(new Balance(new BigDecimal(-10)),account.balance());
    }

    @Test
    void should_withdrawal_amount_10_from_account_with_balance_20(){
        Account account = new Account(new Balance(new BigDecimal(20)));
        Amount amount = new Amount(BigDecimal.TEN);

        account.withdrawal(amount);

        Assertions.assertEquals(new Balance(BigDecimal.TEN),account.balance());
    }

    @Test
    void should_deposit_20_and_withdrawal_10_from_account_with_0_balance(){
        Account account = new Account(new Balance(BigDecimal.ZERO));

        account.deposit(new Amount(new BigDecimal(20)));
        account.withdrawal(new Amount(BigDecimal.TEN));

        Assertions.assertEquals(new Balance(BigDecimal.TEN),account.balance());
    }

}

