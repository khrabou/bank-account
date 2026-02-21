package com.bankaccount.bankaccount.infrastructure.mapper;

import com.bankaccount.bankaccount.domain.model.Account;
import com.bankaccount.bankaccount.domain.model.Amount;
import com.bankaccount.bankaccount.domain.model.Balance;
import com.bankaccount.bankaccount.domain.model.Transaction;
import com.bankaccount.bankaccount.infrastructure.entities.AccountEntity;
import com.bankaccount.bankaccount.infrastructure.entities.TransactionEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account toAccount(AccountEntity accountEntity);

    AccountEntity toAccountEntity(Account account);

    default Balance mapToBalance(BigDecimal value) {
        return new Balance(value);
    }

    default BigDecimal map(Balance balance) {
        return balance.value();
    }

    default Amount mapToAmount(BigDecimal value) {
        return new Amount(value);
    }

    default BigDecimal map(Amount amount) {
        return amount.value();
    }

    @Mappings({
            @Mapping(source = "operation.amount", target = "amount"),
            @Mapping(source = "operation.time", target = "time"),
            @Mapping(source = "operation.operationType", target = "operationType")})
    TransactionEntity map(Transaction transaction);

    @Mappings({
            @Mapping(source = "amount", target = "operation.amount"),
            @Mapping(source = "time", target = "operation.time"),
            @Mapping(source = "operationType", target = "operation.operationType")})
    Transaction map(TransactionEntity transactionEntity);

}
