package com.splitwise.settlement.service;

import com.splitwise.common.event.ExpenseEvent;
import com.splitwise.settlement.algorithm.MinCashFlowSimplifier;
import com.splitwise.settlement.dto.DebtTransaction;
import com.splitwise.settlement.entity.UserBalance;
import com.splitwise.settlement.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final UserBalanceRepository balanceRepository;
    private final MinCashFlowSimplifier simplifier;

    @Transactional
    public void processExpenseEvent(ExpenseEvent event) {
        Long paidBy = event.getPaidByUserId();
        Long groupId = event.getGroupId();

        for (ExpenseEvent.UserSplit split : event.getSplits()) {
            if (split.getUserId().equals(paidBy)) continue; // Skip self

            Long borrower = split.getUserId();
            BigDecimal amount = split.getAmount();

            updateUserBalance(groupId, borrower, paidBy, amount);
            updateUserBalance(groupId, paidBy, borrower, amount.negate());
        }
    }

    private void updateUserBalance(Long groupId, Long userId, Long peerId, BigDecimal delta) {
        UserBalance balance = balanceRepository
                .findByGroupIdAndUserIdAndPeerId(groupId, userId, peerId)
                .orElse(UserBalance.builder()
                        .groupId(groupId)
                        .userId(userId)
                        .peerId(peerId)
                        .amount(BigDecimal.ZERO)
                        .build());

        balance.setAmount(balance.getAmount().add(delta));
        balanceRepository.save(balance);
    }

    public List<DebtTransaction> getSimplifiedGroupDebts(Long groupId) {
        List<UserBalance> groupBalances = balanceRepository.findByGroupId(groupId);

        // Aggregate net balances for each user
        Map<Long, BigDecimal> netBalances = new HashMap<>();
        for (UserBalance b : groupBalances) {
            netBalances.merge(b.getUserId(), b.getAmount(), BigDecimal::add);
        }

        return simplifier.simplifyDebts(netBalances);
    }

    public List<UserBalance> getUserBalances(Long userId) {
        return balanceRepository.findByUserId(userId);
    }
}