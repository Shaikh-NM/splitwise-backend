package com.splitwise.expense.service;

import com.splitwise.common.event.ExpenseEvent;
import com.splitwise.expense.dto.CreateExpenseRequest;
import com.splitwise.expense.entity.Expense;
import com.splitwise.expense.entity.ExpenseSplit;
import com.splitwise.expense.kafka.ExpenseEventProducer;
import com.splitwise.expense.repository.ExpenseRepository;
import com.splitwise.expense.strategy.SplitStrategy;
import com.splitwise.expense.strategy.SplitStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final SplitStrategyFactory splitStrategyFactory;
    private final ExpenseEventProducer eventProducer;

    @Transactional
    public Expense createExpense(CreateExpenseRequest request) {
        SplitStrategy strategy = splitStrategyFactory.getStrategy(request.getSplitType());
        List<ExpenseSplit> calculatedSplits = strategy.calculateSplits(request.getTotalAmount(), request.getSplits());

        Expense expense = Expense.builder()
                .description(request.getDescription())
                .totalAmount(request.getTotalAmount())
                .paidByUserId(request.getPaidByUserId())
                .groupId(request.getGroupId())
                .splitType(request.getSplitType())
                .build();

        calculatedSplits.forEach(split -> split.setExpense(expense));
        expense.setSplits(calculatedSplits);

        Expense savedExpense = expenseRepository.save(expense);

        // Publish event to Kafka for Settlement & Notification Services
        ExpenseEvent event = ExpenseEvent.builder()
                .expenseId(savedExpense.getId())
                .paidByUserId(savedExpense.getPaidByUserId())
                .groupId(savedExpense.getGroupId())
                .totalAmount(savedExpense.getTotalAmount())
                .splits(savedExpense.getSplits().stream()
                        .map(s -> new ExpenseEvent.UserSplit(s.getUserId(), s.getAmount()))
                        .collect(Collectors.toList()))
                .build();

        eventProducer.publishExpenseEvent(event);

        return savedExpense;
    }

    public List<Expense> getExpensesByGroup(Long groupId) {
        return expenseRepository.findByGroupId(groupId);
    }
}