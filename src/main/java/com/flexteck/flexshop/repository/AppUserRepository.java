package com.flexteck.flexshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexteck.flexshop.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Boolean existsByUsernameIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);

    Optional<AppUser> findByEmailIgnoreCase(String email);
}
