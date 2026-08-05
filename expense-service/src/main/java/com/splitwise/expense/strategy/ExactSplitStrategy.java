package com.splitwise.expense.strategy;

import com.splitwise.expense.dto.SplitRequest;
import com.splitwise.expense.entity.ExpenseSplit;
import com.splitwise.expense.entity.SplitType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExactSplitStrategy implements SplitStrategy {
    @Override
    public SplitType getSplitType() {
        return SplitType.EXACT;
    }

    @Override
    public List<ExpenseSplit> calculateSplits(BigDecimal totalAmount, List<SplitRequest> splitRequests) {
        BigDecimal sumExact = splitRequests.stream()
                .map(SplitRequest::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (sumExact.compareTo(totalAmount) != 0) {
            throw new IllegalArgumentException("Sum of exact amounts (" + sumExact + ") does not equal total (" + totalAmount + ")");
        }

        return splitRequests.stream().map(req -> {
            ExpenseSplit split = new ExpenseSplit();
            split.setUserId(req.getUserId());
            split.setAmount(req.getAmount());
            return split;
        }).collect(Collectors.toList());
    }
}