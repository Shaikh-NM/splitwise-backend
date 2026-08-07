package com.splitwise.notification.service;

import com.splitwise.common.event.ExpenseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailNotificationService {

    public void sendExpenseNotification(ExpenseEvent event) {
        log.info("[NOTIFICATION SENT] Expense '{}' of amount ${} recorded by User {}. Notifying {} participants.",
                event.getDescription(),
                event.getTotalAmount(),
                event.getPaidByUserId(),
                event.getSplits().size());

        // Integration hook for JavaMailSender or SendGrid API
    }
}