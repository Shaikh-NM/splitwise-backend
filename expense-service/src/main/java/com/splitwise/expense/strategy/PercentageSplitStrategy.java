package com.splitwise.expense.strategy;

import com.splitwise.expense.dto.SplitRequest;
import com.splitwise.expense.entity.ExpenseSplit;
import com.splitwise.expense.entity.SplitType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PercentageSplitStrategy implements SplitStrategy {
    @Override
    public SplitType getSplitType() {
        return SplitType.PERCENTAGE;
    }

    @Override
    public List<ExpenseSplit> calculateSplits(BigDecimal totalAmount, List<SplitRequest> splitRequests) {
        BigDecimal totalPercentage = splitRequests.stream()
                .map(SplitRequest::getPercentage)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPercentage.compareTo(new BigDecimal("100.00")) != 0) {
            throw new IllegalArgumentException("Sum of percentages must equal 100%");
        }

        return splitRequests.stream().map(req -> {
            BigDecimal splitAmount = totalAmount.multiply(req.getPercentage())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

            ExpenseSplit split = new ExpenseSplit();
            split.setUserId(req.getUserId());
            split.setAmount(splitAmount);
            return split;
        }).collect(Collectors.toList());
    }
}