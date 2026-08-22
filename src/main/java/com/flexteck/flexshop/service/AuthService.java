package com.flexteck.flexshop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flexteck.flexshop.dto.request.LoginRequest;
import com.flexteck.flexshop.dto.request.RegisterRequest;
import com.flexteck.flexshop.dto.response.AuthResponse;
import com.flexteck.flexshop.dto.response.UserProfileResponse;
import com.flexteck.flexshop.entity.AppUser;
import com.flexteck.flexshop.enums.Role;
import com.flexteck.flexshop.exception.DuplicateResourceException;
import com.flexteck.flexshop.repository.AppUserRepository;
import com.flexteck.flexshop.exception.InvalidCredentialsException;
import com.flexteck.flexshop.exception.ResourceNotFoundException;

@Service
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
        String token = jwtService.generateToken(savedUser);
        return new AuthResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().name(),
                "Bearer",
                token,
                "User registered successfully");
    }

    public AuthResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid Email or password"));

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException("Invalid Email or password");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                "Bearer",
                token,
                "Login Successful");
    }

    public UserProfileResponse getLoggedInUser(String email) {
        AppUser user = appUserRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.getCreatedAt());
    }

}
