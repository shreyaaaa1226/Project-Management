package com.shreya.service;

import com.shreya.model.PasswordResetToken;

public interface PasswordResetTokenService {

	PasswordResetToken findByToken(String token);

	void delete(PasswordResetToken resetToken);

}
