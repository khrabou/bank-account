package com.kata.bankaccount.infrastructure;

import com.kata.bankaccount.business.Account;
import com.kata.bankaccount.business.Amount;
import com.kata.bankaccount.business.Balance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ConsoleStatementPrinterTest {

    Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeEach
    void setup(){
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
    }


    @Test
    void should_print_deposit_statement_line(){

        Account account = new Account(new Balance(BigDecimal.ZERO),fixedClock);
        account.deposit(new Amount(BigDecimal.TEN));

        account.print(new ConsoleStatementPrinter());

        String expectedDate = LocalDateTime.now(fixedClock).format(formatter);
        String expectedOut = "| OPERATION | DATE | AMOUNT | BALANCE |\n| DEPOSIT | "+expectedDate+" | 10 | 10 |\n";
        Assertions.assertEquals(expectedOut, outContent.toString());
    }

    @Test
    void print_deposit_and_withdrawal_statement(){

        Account account = new Account(new Balance(BigDecimal.ZERO),fixedClock);
        account.deposit(new Amount(new BigDecimal(20)));
        account.withdrawal(new Amount(BigDecimal.TEN));

        account.print(new ConsoleStatementPrinter());

        String expectedDate = LocalDateTime.now(fixedClock).format(formatter);
        String expectedOut = "| OPERATION | DATE | AMOUNT | BALANCE |\n| DEPOSIT | "+expectedDate+" | 20 | 20 |\n| WITHDRAWAL | "+expectedDate+" | 10 | 10 |\n";
        Assertions.assertEquals(expectedOut, outContent.toString());
    }
}
