package com.csjmu.arena.repository;

import com.csjmu.arena.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.csjmu.arena.entity.Role;
import java.util.Optional;



public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    long countByRole(Role role);
}