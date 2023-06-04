package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karaban.social_media_res_api.model.ChatDto;
import ru.karaban.social_media_res_api.model.SendMessage;
import ru.karaban.social_media_res_api.service.ChatService;
import ru.karaban.social_media_res_api.utils.GetUsernameJwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/content/chat")
@RequiredArgsConstructor
public class MessageController {

    private final GetUsernameJwtTokenUtil usernameByToken;
    private final ChatService chatService;

    @GetMapping()
    public ResponseEntity<?> getMessages(HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        List<ChatDto> chats = chatService.getMessages(username);
        return ResponseEntity.ok(chats);
    }

    @PostMapping()
    public ResponseEntity<?> sendMessage(HttpServletRequest request, @Valid @RequestBody SendMessage message) {

        String username = usernameByToken.getUsernameByToken(request);
        chatService.saveChat(username, message);
        return ResponseEntity.ok("Message send");
    }
}
