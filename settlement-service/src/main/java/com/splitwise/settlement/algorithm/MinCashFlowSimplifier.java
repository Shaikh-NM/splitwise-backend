package com.splitwise.settlement.algorithm;

import com.splitwise.settlement.dto.DebtTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class MinCashFlowSimplifier {

    @Getter
    @AllArgsConstructor
    private static class UserAmount {
        private final Long userId;
        private final BigDecimal amount;
    }

    public List<DebtTransaction> simplifyDebts(Map<Long, BigDecimal> netBalances) {
        List<DebtTransaction> transactions = new ArrayList<>();

        // Separate debtors (net negative) and creditors (net positive)
        PriorityQueue<UserAmount> debtors = new PriorityQueue<>(Comparator.comparing(UserAmount::getAmount));
        PriorityQueue<UserAmount> creditors = new PriorityQueue<>((a, b) -> b.getAmount().compareTo(a.getAmount()));

        for (Map.Entry<Long, BigDecimal> entry : netBalances.entrySet()) {
            if (entry.getValue().compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(new UserAmount(entry.getKey(), entry.getValue().abs()));
            } else if (entry.getValue().compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(new UserAmount(entry.getKey(), entry.getValue()));
            }
        }

        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            UserAmount debtor = debtors.poll();
            UserAmount creditor = creditors.poll();

            BigDecimal minAmount = debtor.getAmount().min(creditor.getAmount());

            transactions.add(new DebtTransaction(debtor.getUserId(), creditor.getUserId(), minAmount));

            BigDecimal debtorRemaining = debtor.getAmount().subtract(minAmount);
            BigDecimal creditorRemaining = creditor.getAmount().subtract(minAmount);

            if (debtorRemaining.compareTo(BigDecimal.ZERO) > 0) {
                debtors.add(new UserAmount(debtor.getUserId(), debtorRemaining));
            }
            if (creditorRemaining.compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(new UserAmount(creditor.getUserId(), creditorRemaining));
            }
        }

        return transactions;
    }
}