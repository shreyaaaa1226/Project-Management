package com.shreya.service;

import java.util.List;

import com.shreya.exception.ChatException;
import com.shreya.exception.ProjectException;
import com.shreya.exception.UserException;
import com.shreya.model.Message;

public interface MessageService {

    Message sendMessage(Long senderId, Long chatId, String content) throws UserException, ChatException, ProjectException;

    List<Message> getMessagesByProjectId(Long projectId) throws ProjectException, ChatException;
}

