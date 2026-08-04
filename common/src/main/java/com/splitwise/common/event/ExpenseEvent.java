package com.splitwise.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseEvent {
    private String eventId;
    private String eventType; // CREATED, EDITED, DELETED
    private Long expenseId;
    private Long paidByUserId;
    private Long groupId; // Nullable for 1-on-1 expenses
    private BigDecimal totalAmount;
    private String splitType; // EQUAL, EXACT, PERCENTAGE
    private List<UserSplit> splits;
    private Instant timestamp;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSplit {
        private Long userId;
        private BigDecimal amount;
    }
}