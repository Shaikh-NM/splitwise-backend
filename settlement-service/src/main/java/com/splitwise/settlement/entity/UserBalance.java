package com.splitwise.settlement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "user_balances", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"group_id", "user_id", "peer_id"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long groupId; // Nullable for direct global balance

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long peerId;

    // Positive means userId owes peerId; negative means peerId owes userId
    @Column(nullable = false)
    private BigDecimal amount;
}