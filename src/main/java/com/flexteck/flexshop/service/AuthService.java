package com.flexteck.flexshop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flexteck.flexshop.dto.request.LoginRequest;
import com.flexteck.flexshop.dto.request.RegisterRequest;
import com.flexteck.flexshop.dto.response.AuthResponse;
import com.flexteck.flexshop.entity.AppUser;
import com.flexteck.flexshop.enums.Role;
import com.flexteck.flexshop.exception.DuplicateResourceException;
import com.flexteck.flexshop.repository.AppUserRepository;
import com.flexteck.flexshop.exception.InvalidCredentialsException;

@Service
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.existsByUsernameIgnoreCase(request.username())) {
            throw new DuplicateResourceException("Username already exist: " + request.username());
        }

        if (appUserRepository.existsByEmailIgnoreCase(request.email())) {
            throw new DuplicateResourceException("Email already exist: " + request.email());
        }

        AppUser user = new AppUser(request.username(), request.email(), passwordEncoder.encode(request.password()),
                Role.USER);
        AppUser savedUser = appUserRepository.save(user);
        return new AuthResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().name(),
                "User registered successfully");
    }

    public AuthResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid Email or password"));

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException("Invalid Email or password");
        }

        return new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                "Login Successful");
    }

}
