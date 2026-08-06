package com.splitwise.settlement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebtTransaction {
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
}