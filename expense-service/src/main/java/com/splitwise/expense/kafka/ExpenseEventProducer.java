package com.splitwise.expense.kafka;

import com.splitwise.common.event.ExpenseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpenseEventProducer {
    private static final String TOPIC = "expense-events";
    private final KafkaTemplate<String, ExpenseEvent> kafkaTemplate;

    public void publishExpenseEvent(ExpenseEvent event) {
        kafkaTemplate.send(TOPIC, String.valueOf(event.getExpenseId()), event);
    }
}
