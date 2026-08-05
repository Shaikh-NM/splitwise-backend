package com.splitwise.usergroup.service;

import com.splitwise.usergroup.entity.Friendship;
import com.splitwise.usergroup.entity.Group;
import com.splitwise.usergroup.entity.User;
import com.splitwise.usergroup.repository.FriendshipRepository;
import com.splitwise.usergroup.repository.GroupRepository;
import com.splitwise.usergroup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGroupService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final FriendshipRepository friendshipRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    @Transactional
    public void addFriend(Long userId, Long friendId) {
        User user = getUser(userId);
        User friend = getUser(friendId);

        if (!friendshipRepository.existsByUserAndFriend(user, friend)) {
            friendshipRepository.save(Friendship.builder().user(user).friend(friend).build());
            friendshipRepository.save(Friendship.builder().user(friend).friend(user).build());
        }
    }

    public List<User> getFriends(Long userId) {
        User user = getUser(userId);
        return friendshipRepository.findByUser(user).stream()
                .map(Friendship::getFriend)
                .collect(Collectors.toList());
    }

    public Group createGroup(String groupName, List<Long> memberIds) {
        List<User> members = userRepository.findAllById(memberIds);
        Group group = Group.builder()
                .name(groupName)
                .members(members.stream().collect(Collectors.toSet()))
                .build();
        return groupRepository.save(group);
    }

    @Transactional
    public Group addMemberToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found: " + groupId));
        User user = getUser(userId);
        group.getMembers().add(user);
        return groupRepository.save(group);
    }
}