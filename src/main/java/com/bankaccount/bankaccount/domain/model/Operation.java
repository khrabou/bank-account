package com.bankaccount.bankaccount.domain.model;

import java.time.LocalDateTime;

public record Operation(OperationType operationType, Amount amount, LocalDateTime time) {

}

