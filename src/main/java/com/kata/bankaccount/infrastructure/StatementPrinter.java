package com.kata.bankaccount.infrastructure;

import com.kata.bankaccount.business.Statement;

public interface StatementPrinter {
    void print(Statement statement);
}
