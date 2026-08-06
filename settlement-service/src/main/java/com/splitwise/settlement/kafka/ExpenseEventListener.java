package com.splitwise.settlement.kafka;

import com.splitwise.common.event.ExpenseEvent;
import com.splitwise.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpenseEventListener {
    private final SettlementService settlementService;

    @KafkaListener(topics = "expense-events", groupId = "settlement-group")
    public void handleExpenseEvent(ExpenseEvent event) {
        log.info("Received expense event for expense ID: {}", event.getExpenseId());
        settlementService.processExpenseEvent(event);
    }
}