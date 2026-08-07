package com.splitwise.notification.kafka;

import com.splitwise.common.event.ExpenseEvent;
import com.splitwise.notification.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpenseNotificationListener {
    private final EmailNotificationService notificationService;

    @KafkaListener(topics = "expense-events", groupId = "notification-group")
    public void consumeExpenseEvent(ExpenseEvent event) {
        log.info("Received expense event notification for expense ID: {}", event.getExpenseId());
        notificationService.sendExpenseNotification(event);
    }
}