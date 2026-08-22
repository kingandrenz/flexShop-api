package com.flexteck.flexshop.dto.response;

public record AuthResponse(
                Long id,
                String username,
                String email,
                String role,
                String tokenType,
                String token,
                String message) {

}
