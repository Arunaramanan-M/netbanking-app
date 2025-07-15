package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean register(User user, String roleName) {
        if (userRepo.findByEmail(user.getEmail()) != null) return false;

        Role role = roleRepo.findByRoleName(roleName);
        if (role == null) throw new RuntimeException("Role not found: " + roleName);

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole(role);
        user.setTwoFaEnabled(1); // enable 2FA for all users by default, or make it configurable
        userRepo.save(user);
        return true;
    }
    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

}
