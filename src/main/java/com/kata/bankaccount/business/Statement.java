package com.kata.bankaccount.business;

import com.kata.bankaccount.infrastructure.StatementPrinter;
import java.util.ArrayList;
import java.util.List;

public class Statement {
    private List<StatementLine> statementLines = new ArrayList<>();

    public void print(StatementPrinter statementPrinter) {
        statementPrinter.print(this);
    }

    public List<StatementLine> statementLines() {
        return statementLines;
    }

    public void add(Operation operation, Balance balance) {
        statementLines.add(new StatementLine(operation, balance));
    }
}
