package com.splitwise.usergroup.controller;

import com.splitwise.usergroup.entity.Group;
import com.splitwise.usergroup.entity.User;
import com.splitwise.usergroup.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserGroupController {
    private final UserGroupService userGroupService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userGroupService.createUser(user));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userGroupService.getUser(id));
    }

    @PostMapping("/users/{userId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userGroupService.addFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}/friends")
    public ResponseEntity<List<User>> getFriends(@PathVariable Long userId) {
        return ResponseEntity.ok(userGroupService.getFriends(userId));
    }

    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(@RequestParam String name, @RequestBody List<Long> memberIds) {
        return ResponseEntity.ok(userGroupService.createGroup(name, memberIds));
    }

    @PostMapping("/groups/{groupId}/members/{userId}")
    public ResponseEntity<Group> addMemberToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        return ResponseEntity.ok(userGroupService.addMemberToGroup(groupId, userId));
    }
}