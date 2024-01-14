package com.bankaccount.bankaccount.infrastructure.mapper;

import com.bankaccount.bankaccount.domain.model.*;
import com.bankaccount.bankaccount.infrastructure.entities.AccountEntity;
import com.bankaccount.bankaccount.infrastructure.entities.TransactionEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccountMapperTest {

    @Test
    void should_map_to_AccountEntity(){
        long accountId= 15L;
        Transaction transaction = new Transaction(new Operation(OperationType.DEPOSIT, new Amount(BigDecimal.TEN),
                LocalDateTime.of(2024,1,13,5,4)), new Balance(BigDecimal.TEN));

        Account account = new Account(accountId, new Balance(BigDecimal.TEN), List.of(transaction));

        AccountEntity accountEntity = AccountMapper.INSTANCE.toAccountEntity(account);

        assertThat(accountEntity.getAccountId()).isEqualTo(account.accountId());
        assertThat(accountEntity.getBalance()).isEqualTo(account.balance().value());
        assertThat(accountEntity.getTransactions().size()).isEqualTo(account.transactions().size());
        assertThat(accountEntity.getTransactions().getFirst().getBalance()).isEqualTo(account.transactions().getFirst().balance().value());
        assertThat(accountEntity.getTransactions().getFirst().getTime()).isEqualTo(account.transactions().getFirst().operation().time());
        assertThat(accountEntity.getTransactions().getFirst().getAmount()).isEqualTo(account.transactions().getFirst().operation().amount().value());
        assertThat(accountEntity.getTransactions().getFirst().getOperationType()).isEqualTo(account.transactions().getFirst().operation().operationType());


    }

    @Test
    void should_map_to_Account(){
        long accountId= 15L;

        TransactionEntity transactionEntity = new TransactionEntity(12L, OperationType.DEPOSIT, BigDecimal.TEN,
                LocalDateTime.of(2024,1,13,5,4), BigDecimal.TEN);

        AccountEntity accountEntity = new AccountEntity(accountId, BigDecimal.TEN, List.of(transactionEntity));

        Account account = AccountMapper.INSTANCE.toAccount(accountEntity);

        assertThat(account.accountId()).isEqualTo(accountEntity.getAccountId());
        assertThat(account.balance().value()).isEqualTo(accountEntity.getBalance());
        assertThat(account.transactions().size()).isEqualTo(accountEntity.getTransactions().size());
        assertThat(account.transactions().getFirst().balance().value()).isEqualTo(accountEntity.getTransactions().getFirst().getBalance());
        assertThat(account.transactions().getFirst().operation().time()).isEqualTo(accountEntity.getTransactions().getFirst().getTime());
        assertThat(account.transactions().getFirst().operation().amount().value()).isEqualTo(accountEntity.getTransactions().getFirst().getAmount());
        assertThat(account.transactions().getFirst().operation().operationType()).isEqualTo(accountEntity.getTransactions().getFirst().getOperationType());


    }

}