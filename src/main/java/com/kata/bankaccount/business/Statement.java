package com.kata.bankaccount.business;

import com.kata.bankaccount.infrastructure.StatementPrinter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Statement {
    private List<StatementLine> statementLines = new ArrayList<>();

    public void add(StatementLine statementLine) {
        statementLines.add(statementLine);
    }

    public void print(StatementPrinter statementPrinter) {
        statementPrinter.print(this);
    }

    public List<StatementLine> statementLines() {
        return statementLines;
    }

    public void add(OperationType operationType, Amount amount, LocalDateTime date, Balance balance) {
        statementLines.add(new StatementLine(operationType, amount, date, balance));
    }
}
