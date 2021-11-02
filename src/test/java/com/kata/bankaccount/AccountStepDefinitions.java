package com.kata.bankaccount;

import com.kata.bankaccount.business.Account;
import com.kata.bankaccount.business.Amount;
import com.kata.bankaccount.business.Balance;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class AccountStepDefinitions {


    private final Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.of("Europe/Paris"));
    private Account account;

    @Given("^Account with balance of (\\d+)$")
    public void account_with_balance_of(int balance) {
        account = new Account(new Balance(new BigDecimal(balance)),fixedClock);
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
        Assertions.assertEquals(new Balance(new BigDecimal(expectedBalance)),account.balance());
    }


}
