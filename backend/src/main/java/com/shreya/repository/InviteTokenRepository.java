package com.shreya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreya.model.Invitation;

public interface InviteTokenRepository extends JpaRepository<Invitation, Long> {

	void deleteByToken(String token);

    Invitation findByToken(String token);

	Invitation findByEmail(String userEmail);
}
