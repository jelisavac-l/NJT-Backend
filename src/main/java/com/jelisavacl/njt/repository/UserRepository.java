package com.jelisavacl.njt.repository;

import com.jelisavacl.njt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByConfirmationToken(String token);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
