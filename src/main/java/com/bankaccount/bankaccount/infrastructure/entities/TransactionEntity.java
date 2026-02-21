package com.bankaccount.bankaccount.infrastructure.entities;

import com.bankaccount.bankaccount.domain.model.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "type")
    private OperationType operationType;


    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "balance")
    private BigDecimal balance;
}
