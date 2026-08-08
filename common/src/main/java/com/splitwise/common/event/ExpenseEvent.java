package com.splitwise.common.event;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseEvent {
    private Long expenseId;
    private Long groupId;
    private Long paidByUserId;
    private BigDecimal totalAmount;
    private String description; // <-- Added field
    private List<UserSplit> splits;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSplit {
        private Long userId;
        private BigDecimal amount;
    }
}