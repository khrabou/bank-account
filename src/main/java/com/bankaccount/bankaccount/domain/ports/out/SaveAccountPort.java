package com.bankaccount.bankaccount.domain.ports.out;

import com.bankaccount.bankaccount.domain.model.Account;


public interface SaveAccountPort {

    void save(Account account);

}
