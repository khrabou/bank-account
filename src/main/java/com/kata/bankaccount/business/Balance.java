package com.kata.bankaccount.business;

import java.math.BigDecimal;

public record Balance(BigDecimal value) {
    public Balance add(Amount amount) {
        return new Balance(value.add(amount.value()));
    }

    public Balance subtract(Amount amount) {
        return new Balance(value.subtract(amount.value()));
    }
}
