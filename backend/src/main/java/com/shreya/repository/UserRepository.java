package com.shreya.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.shreya.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);

}
