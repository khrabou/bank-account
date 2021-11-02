package com.kata.bankaccount.infrastructure;

import com.kata.bankaccount.business.Statement;
import com.kata.bankaccount.business.StatementLine;

import java.time.format.DateTimeFormatter;

public class ConsoleStatementPrinter implements StatementPrinter {

    private String header = "| OPERATION | DATE | AMOUNT | BALANCE |\n";
    private String line = "| %s | %s | %s | %s |\n";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void print(Statement statement) {
        StringBuilder stringBuilder = new StringBuilder(header);
        statement.statementLines().forEach(l->{
            String printedLine = String.format(line,
                    l.operation().operationType(),
                    l.operation().date().format(formatter),
                    l.operation().amount().value(),
                    l.balance().value());
            stringBuilder.append(printedLine);
        });
        System.out.print(stringBuilder);
    }
}
