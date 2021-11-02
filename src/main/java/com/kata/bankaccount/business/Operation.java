package com.kata.bankaccount.business;

import java.time.LocalDateTime;

public record Operation(OperationType operationType, Amount amount, LocalDateTime date) {

}
