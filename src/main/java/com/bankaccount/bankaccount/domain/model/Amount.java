package com.bankaccount.bankaccount.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Amount(BigDecimal value) {

    public static final String AMOUNT_MUST_NOT_BE_NEGATIVE = "Amount must not be negative";

    public Amount{
        Objects.requireNonNull(value);
        if(value.signum()==-1)
            throw new IllegalArgumentException(AMOUNT_MUST_NOT_BE_NEGATIVE);
    }
}
