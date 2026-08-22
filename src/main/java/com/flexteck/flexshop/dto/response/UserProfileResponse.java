package com.flexteck.flexshop.dto.response;

import java.time.LocalDateTime;

public record UserProfileResponse(
        Long id,
        String username,
        String email,
        String role,
        LocalDateTime createdAt) {

}
