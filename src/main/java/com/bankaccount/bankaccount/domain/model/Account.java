package com.bankaccount.bankaccount.domain.model;

import java.util.ArrayList;
import java.util.List;

public record Account(long accountId, Balance balance, List<Transaction> transactions) {
    public Account(long accountId, Balance balance) {
        this(accountId, balance, new ArrayList<>());
    }
}
