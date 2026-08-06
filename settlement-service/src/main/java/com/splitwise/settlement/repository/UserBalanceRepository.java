package com.splitwise.settlement.repository;

import com.splitwise.settlement.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {
    List<UserBalance> findByGroupId(Long groupId);
    Optional<UserBalance> findByGroupIdAndUserIdAndPeerId(Long groupId, Long userId, Long peerId);
    List<UserBalance> findByUserId(Long userId);
}