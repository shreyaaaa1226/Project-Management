package com.shreya.service;

import com.shreya.exception.MailsException;
import com.shreya.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

	void sendInvitation(String email, Long projectId) throws MailsException, MessagingException;

	Invitation acceptInvitation(String token, Long userId) throws Exception;
}
