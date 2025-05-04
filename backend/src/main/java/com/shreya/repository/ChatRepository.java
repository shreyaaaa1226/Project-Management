package com.shreya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreya.model.Chat;
import com.shreya.model.Project;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    

	Chat findByProject(Project projectById);
	
//	List<Chat> findByProjectNameContainingIgnoreCase(String projectName);
}

