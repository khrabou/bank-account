package com.bankaccount.bankaccount.domain.ports.in;

import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.Amount;

public interface DepositUseCase {

    void deposit(long accountId, Amount amount) throws ResourceNotFoundException;
}
