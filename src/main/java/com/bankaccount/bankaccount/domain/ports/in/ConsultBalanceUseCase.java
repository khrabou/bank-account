package com.bankaccount.bankaccount.domain.ports.in;

import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.Balance;

public interface ConsultBalanceUseCase {

    Balance consultBalance(long accountId) throws ResourceNotFoundException;
}
