package com.bankaccount.bankaccount.domain.ports.in;

import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.Transaction;

import java.util.List;

public interface ConsultTransactionsUseCase {

    List<Transaction> transactions(long accountId) throws ResourceNotFoundException;
}
