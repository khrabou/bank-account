package com.bankaccount.bankaccount.infrastructure.h2;

import com.bankaccount.bankaccount.infrastructure.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface H2AccountRepository extends JpaRepository<AccountEntity, Long> {
}
