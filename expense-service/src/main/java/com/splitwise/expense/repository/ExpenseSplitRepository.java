package com.splitwise.expense.repository;

import com.splitwise.expense.entity.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {
    List<ExpenseSplit> findByUserId(Long userId);
    List<ExpenseSplit> findByExpenseId(Long expenseId);
}