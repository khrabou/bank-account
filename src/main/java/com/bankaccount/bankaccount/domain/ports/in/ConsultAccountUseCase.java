package com.bankaccount.bankaccount.domain.ports.in;

import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.Account;

public interface ConsultAccountUseCase {

    Account account(long accountId) throws ResourceNotFoundException;
}
