package com.jelisavacl.njt.service;

import com.jelisavacl.njt.dto.UserDTO;
import com.jelisavacl.njt.entity.User;
import com.jelisavacl.njt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        // You could add validation or password hashing here
        return userRepository.save(user);
    }

    public UserDTO updateUser(Long id, User updatedUser) {
        User u = userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return UserDTO.toDTO(u);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserDTOByUsername(String username) {
        User u = userRepository.findByUsername(username)
            .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
        return UserDTO.toDTO(u);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
    }
}
