package com.splitwise.expense.dto;

import com.splitwise.expense.entity.SplitType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateExpenseRequest {
    private String description;
    private BigDecimal totalAmount;
    private Long paidByUserId;
    private Long groupId; // Nullable if direct friend expense
    private SplitType splitType;
    private List<SplitRequest> splits;
}