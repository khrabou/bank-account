package com.kata.bankaccount.business;

import com.kata.bankaccount.infrastructure.StatementPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AccountTest {

    private final static Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

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

    @Test
    void should_print_deposit_statement_line(){
        Account account = new Account(new Balance(BigDecimal.ZERO),fixedClock);
        FakeStatementPrinter statementPrinter=new FakeStatementPrinter();
        account.deposit(new Amount(BigDecimal.TEN));

        account.print(statementPrinter);

        Amount amount=new Amount(BigDecimal.TEN);
        Balance balance=new Balance(BigDecimal.TEN);
        Assertions.assertTrue(statementPrinter.lines.contains(new StatementLine(OperationType.DEPOSIT,amount, LocalDateTime.now(fixedClock), balance)));
    }

    @Test
    void should_print_withdrawal_statement_line(){
        Account account = new Account(new Balance(new BigDecimal(20)),fixedClock);
        FakeStatementPrinter statementPrinter=new FakeStatementPrinter();
        account.withdrawal(new Amount(BigDecimal.TEN));

        account.print(statementPrinter);

        Amount amount=new Amount(BigDecimal.TEN);
        Balance balance=new Balance(BigDecimal.TEN);
        Assertions.assertTrue(statementPrinter.lines.contains(new StatementLine(OperationType.WITHDRAWAL,amount, LocalDateTime.now(fixedClock), balance)));
    }

    private static class FakeStatementPrinter implements StatementPrinter {

        private final List<StatementLine> lines = new ArrayList<>();

        @Override
        public void print(Statement statement) {
            lines.addAll(statement.statementLines());
        }
    }

}

