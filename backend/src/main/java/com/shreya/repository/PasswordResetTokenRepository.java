package com.shreya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreya.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	PasswordResetToken findByToken(String token);
}
