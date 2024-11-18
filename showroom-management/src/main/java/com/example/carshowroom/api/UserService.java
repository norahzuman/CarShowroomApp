package com.example.carshowroom.api;

import com.example.carshowroom.api.models.LoginRequest;
import com.example.carshowroom.dao.models.RoleEntity;
import com.example.carshowroom.dao.models.UserEntity;
import com.example.carshowroom.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Map<String, Object> authenticate(LoginRequest loginRequest) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        UserEntity user = userOptional.get();

        // Verify the password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("roles", user.getRoles().stream().map(RoleEntity::getName).toList());

        return response;
    }
}
