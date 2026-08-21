package com.flexteck.flexshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flexteck.flexshop.dto.request.LoginRequest;
import com.flexteck.flexshop.dto.response.AuthResponse;
import com.flexteck.flexshop.entity.AppUser;
import com.flexteck.flexshop.enums.Role;
import com.flexteck.flexshop.exception.InvalidCredentialsException;
import com.flexteck.flexshop.repository.AppUserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private static final String EMAIL = "flexteckse@gmail.com";
    private static final String RAW_PASSWORD = "@Akaka1na5";
    private static final String ENCODED_PASSWORD = "$2a$10$encodedPassword";

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(appUserRepository, passwordEncoder);
    }

    @Test
    void loginChecksRawPasswordAgainstStoredEncodedPassword() {
        AppUser user = new AppUser("flexteck", EMAIL, ENCODED_PASSWORD, Role.USER);
        LoginRequest request = new LoginRequest(EMAIL, RAW_PASSWORD);
        when(appUserRepository.findByEmailIgnoreCase(EMAIL)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(RAW_PASSWORD, ENCODED_PASSWORD)).thenReturn(true);

        AuthResponse response = authService.login(request);

        assertEquals("Login Successful", response.message());
        verify(passwordEncoder).matches(RAW_PASSWORD, ENCODED_PASSWORD);
    }

    @Test
    void loginRejectsAnIncorrectPassword() {
        AppUser user = new AppUser("flexteck", EMAIL, ENCODED_PASSWORD, Role.USER);
        LoginRequest request = new LoginRequest(EMAIL, "wrong-password");
        when(appUserRepository.findByEmailIgnoreCase(EMAIL)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", ENCODED_PASSWORD)).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.login(request));
    }
}
