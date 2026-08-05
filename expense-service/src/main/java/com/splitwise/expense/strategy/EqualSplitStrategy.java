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
public class EqualSplitStrategy implements SplitStrategy {
    @Override
    public SplitType getSplitType() {
        return SplitType.EQUAL;
    }

    @Override
    public List<ExpenseSplit> calculateSplits(BigDecimal totalAmount, List<SplitRequest> splitRequests) {
        int userCount = splitRequests.size();
        BigDecimal equalShare = totalAmount.divide(BigDecimal.valueOf(userCount), 2, RoundingMode.DOWN);
        BigDecimal remainder = totalAmount.subtract(equalShare.multiply(BigDecimal.valueOf(userCount)));

        return splitRequests.stream().map(req -> {
            ExpenseSplit split = new ExpenseSplit();
            split.setUserId(req.getUserId());
            split.setAmount(equalShare);
            return split;
        }).peek(split -> {
            // Apply penny rounding adjustment to first split if needed
            if (remainder.compareTo(BigDecimal.ZERO) > 0) {
                split.setAmount(split.getAmount().add(remainder));
            }
        }).collect(Collectors.toList());
    }
}