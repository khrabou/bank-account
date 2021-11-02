package com.kata.bankaccount;

import com.kata.bankaccount.business.*;
import com.kata.bankaccount.infrastructure.ConsoleStatementPrinter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccountStepDefinitions {


    private final Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.of("Europe/Paris"));
    private Account account;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    void setup() {
        System.setOut(new PrintStream(outContent));
    }

    public void restore() {
        System.setOut(originalOut);
    }

    @Given("^Account with balance of (\\d+)$")
    public void account_with_balance_of(int balance) {
        account = new Account(new Balance(new BigDecimal(balance)), fixedClock);
    }

    @When("^Deposit amount of (\\d+)$")
    public void deposit_amount_of(int amount) {
        account.deposit(new Amount(new BigDecimal(amount)));
    }

    @When("^Withdrawal amount of (\\d+)$")
    public void withdrawal_amount_of(int amount) {
        account.withdrawal(new Amount(new BigDecimal(amount)));
    }

    @Then("^balance equal to (\\d+)$")
    public void balance_equal_to(int expectedBalance) {
        Assertions.assertEquals(new Balance(new BigDecimal(expectedBalance)), account.balance());
    }


    @When("print statement")
    public void print_statement() {
        setup();
        account.print(new ConsoleStatementPrinter());
    }

    @Then("console print print the following")
    public void console_print_print_the_following(DataTable dataTable) {
        List<StatementLine> statementLines = formatDataTableToListOfStatement(dataTable);
        String expectedOut = "| OPERATION | DATE | AMOUNT | BALANCE |\n";
        for (StatementLine statementLine : statementLines) {
            expectedOut +="| "+ statementLine.operation().operationType() + " | " + LocalDateTime.now(fixedClock).format(formatter) + " | " + statementLine.operation().amount().value() + " | " + statementLine.balance().value() + " |\n";
        }

        Assertions.assertEquals(expectedOut, outContent.toString());
        restore();
    }

    private List<StatementLine> formatDataTableToListOfStatement(DataTable dataTable) {
        return dataTable.asMaps().stream().map(table -> {
            LocalDateTime localDate = LocalDateTime.parse(table.get("date"), formatter);
            Operation operation = new Operation(OperationType.valueOf(table.get("operation")),
                    new Amount(new BigDecimal(table.get("amount"))),
                    localDate);
            return new StatementLine(operation, new Balance(new BigDecimal(table.get("balance"))));
        }).toList();
    }


}
