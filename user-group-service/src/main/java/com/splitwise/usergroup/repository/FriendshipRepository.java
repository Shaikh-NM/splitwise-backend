package com.splitwise.usergroup.repository;

import com.splitwise.usergroup.entity.Friendship;
import com.splitwise.usergroup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUser(User user);
    boolean existsByUserAndFriend(User user, User friend);
}