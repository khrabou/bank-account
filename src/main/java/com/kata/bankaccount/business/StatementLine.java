package com.kata.bankaccount.business;

import java.time.LocalDateTime;

public record StatementLine(OperationType operationType, Amount amount, LocalDateTime localDateTime, Balance balance) {
}
