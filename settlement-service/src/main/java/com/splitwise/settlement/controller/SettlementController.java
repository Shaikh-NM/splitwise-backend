package com.splitwise.settlement.controller;

import com.splitwise.settlement.dto.DebtTransaction;
import com.splitwise.settlement.entity.UserBalance;
import com.splitwise.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/settlements")
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;

    @GetMapping("/group/{groupId}/simplified")
    public ResponseEntity<List<DebtTransaction>> getSimplifiedDebts(@PathVariable Long groupId) {
        return ResponseEntity.ok(settlementService.getSimplifiedGroupDebts(groupId));
    }

    @GetMapping("/user/{userId}/balances")
    public ResponseEntity<List<UserBalance>> getUserBalances(@PathVariable Long userId) {
        return ResponseEntity.ok(settlementService.getUserBalances(userId));
    }
}