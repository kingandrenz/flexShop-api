package com.flexteck.flexshop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Username is required") @Size(min = 3, max = 100, message = "username must be between 3 and 100 characters") String username,

        @NotBlank(message = "Email is required.") @Email(message = "Email must be valid") String email,

        @NotBlank(message = "Password is required") @Size(min = 6, message = "Password should be at least 6 characters") String password) {

}
