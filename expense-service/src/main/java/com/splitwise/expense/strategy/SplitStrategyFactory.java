package com.splitwise.expense.strategy;

import com.splitwise.expense.entity.SplitType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SplitStrategyFactory {
    private final Map<SplitType, SplitStrategy> strategies;

    public SplitStrategyFactory(List<SplitStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(SplitStrategy::getSplitType, Function.identity()));
    }

    public SplitStrategy getStrategy(SplitType splitType) {
        SplitStrategy strategy = strategies.get(splitType);
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid split type: " + splitType);
        }
        return strategy;
    }
}