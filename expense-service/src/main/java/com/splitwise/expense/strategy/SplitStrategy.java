package com.splitwise.expense.strategy;

import com.splitwise.expense.dto.SplitRequest;
import com.splitwise.expense.entity.ExpenseSplit;
import com.splitwise.expense.entity.SplitType;

import java.math.BigDecimal;
import java.util.List;

public interface SplitStrategy {
    SplitType getSplitType();
    List<ExpenseSplit> calculateSplits(BigDecimal totalAmount, List<SplitRequest> splitRequests);
}