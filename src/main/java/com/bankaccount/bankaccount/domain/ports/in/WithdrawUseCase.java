package com.bankaccount.bankaccount.domain.ports.in;

import com.bankaccount.bankaccount.common.InsufficientFundsException;
import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.Amount;

public interface WithdrawUseCase {

    void withdraw(long accountId, Amount amount) throws ResourceNotFoundException, InsufficientFundsException;

}
