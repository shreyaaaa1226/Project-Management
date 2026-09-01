package com.shreya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreya.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    


}

