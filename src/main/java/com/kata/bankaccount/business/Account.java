package com.kata.bankaccount.business;


public class Account {
    private Balance balance;

    public Account(Balance balance) {
        this.balance = balance;
    }

    public Balance balance() {
        return balance;
    }
}
