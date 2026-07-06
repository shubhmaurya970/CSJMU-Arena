package com.csjmu.arena.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.csjmu.arena.entity.Role;
import com.csjmu.arena.entity.User;
import com.csjmu.arena.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.existsByEmail("admin@csjmu.com")) {
            return;
        }

        User admin = User.builder()
                .fullName("System Administrator")
                .email("admin@csjmu.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);

        System.out.println("====================================");
        System.out.println(" Default Admin Created Successfully ");
        System.out.println(" Email: admin@csjmu.com");
        System.out.println(" Password: admin123");
        System.out.println("====================================");
    }
}
