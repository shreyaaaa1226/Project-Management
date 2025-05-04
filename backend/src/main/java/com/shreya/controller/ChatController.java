//package com.shreya.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.shreya.exception.ChatException;
//import com.shreya.exception.ProjectException;
//import com.shreya.model.Chat;
//import com.shreya.request.CreateChat;
//import com.shreya.service.ChatService;
//
//@RestController
//@RequestMapping("/api/chats")
//public class ChatController {
//
//    @Autowired
//    private ChatService chatService;
//
//    @PostMapping("/create")
//    public ResponseEntity<Chat> createChat(@RequestBody CreateChat chat) throws ProjectException {
//        Chat createChat = chatService.createChat(chat.getProjectId(), chat.getUserIds());
//        return ResponseEntity.ok(createChat);
//    }
//
//    @GetMapping("/project/{projectId}")
//    public ResponseEntity<List<Chat>> getChatsByProjectId(@PathVariable Long projectId) throws ChatException {
//        List<Chat> list = chatService.getChatsByProjectId(projectId);
//        return ResponseEntity.ok(list);
//    }
//    
//    @PostMapping("/{chatId}/addUsers")
//    public ResponseEntity<Chat> addUsersToChat(@PathVariable Long chatId,
//                               @RequestParam List<Long> userIds) throws ChatException {
//           Chat addedUsersToChat = chatService.addUsersToChat(chatId, userIds);
//           return ResponseEntity.ok(addedUsersToChat);
//    }
//    
////    @GetMapping("/search")
////    public ResponseEntity<List<Chat>> searchChatsByName(@RequestParam String name) throws ChatException {
////        List<Chat> chats = chatService.searchChatsByName(name);
////        return ResponseEntity.ok(chats);
////    }
//
//    // Other endpoints as needed
//}

