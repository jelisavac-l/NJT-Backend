package com.jelisavacl.njt.controller;

import com.jelisavacl.njt.entity.User;
import com.jelisavacl.njt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        String currentUsername = getCurrentUsername();
        User existingUser = userService.getUserById(id);

        if (!existingUser.getUsername().equals(currentUsername)) {
            return ResponseEntity.status(403).body("You can only update your own profile.");
        }

        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        String currentUsername = getCurrentUsername();
        User existingUser = userService.getUserById(id);

        if (!existingUser.getUsername().equals(currentUsername)) {
            return ResponseEntity.status(403).body("You can only delete your own profile.");
        }

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
