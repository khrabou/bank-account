package com.bankaccount.bankaccount.infrastructure.h2;

import com.bankaccount.bankaccount.domain.model.Account;
import com.bankaccount.bankaccount.domain.ports.out.FindAccountPort;
import com.bankaccount.bankaccount.domain.ports.out.SaveAccountPort;
import com.bankaccount.bankaccount.infrastructure.entities.AccountEntity;
import com.bankaccount.bankaccount.infrastructure.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepository implements SaveAccountPort, FindAccountPort {

    @Autowired
    H2AccountRepository h2AccountRepository;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public Optional<Account> find(long accountId) {
        return h2AccountRepository.findById(accountId).map(accountEntity -> accountMapper.toAccount(accountEntity));
    }

    @Override
    public void save(Account account) {
        AccountEntity accountEntity = accountMapper.toAccountEntity(account);
        h2AccountRepository.save(accountEntity);
    }
}
