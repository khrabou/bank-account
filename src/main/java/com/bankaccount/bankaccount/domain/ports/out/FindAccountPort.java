package com.bankaccount.bankaccount.domain.ports.out;

import com.bankaccount.bankaccount.domain.model.Account;

import java.util.Optional;

public interface FindAccountPort {
    Optional<Account> find(long accountId);
}
