package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karaban.social_media_res_api.dto.ResponseFriendship;
import ru.karaban.social_media_res_api.model.RequestFriendship;
import ru.karaban.social_media_res_api.service.FriendshipService;
import ru.karaban.social_media_res_api.utils.GetUsernameJwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/content/friendship")
@RequiredArgsConstructor
public class FriendshipController {

    private final GetUsernameJwtTokenUtil usernameByToken;
    private final FriendshipService friendshipService;

    @PutMapping
    public ResponseEntity<?> responseFriendship(HttpServletRequest request, @RequestBody RequestFriendship requestBody) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = friendshipService.responseFriendship(username, requestBody);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<?> findAllFriendshipsByUser(HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        ResponseFriendship response = friendshipService.findAllByUser(username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{friendName}")
    ResponseEntity<?> createFriendship(HttpServletRequest request,@PathVariable String friendName) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = friendshipService.createFriendship(username, friendName);
        return ResponseEntity.ok(response);
    }
}
