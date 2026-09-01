package com.shreya.service;

import com.shreya.exception.ProjectException;
import com.shreya.exception.UserException;
import com.shreya.model.User;

public interface UserService {

	User findUserProfileByJwt(String jwt) throws UserException, ProjectException;
	
	User findUserByEmail(String email) throws UserException;
	
	User findUserById(Long userId) throws UserException;

	User updateUsersProjectSize(User user, int number);

	void updatePassword(User user, String newPassword);

	void sendPasswordResetEmail(User user);


}
