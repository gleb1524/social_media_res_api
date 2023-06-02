package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karaban.social_media_res_api.service.SubscriptionsService;
import ru.karaban.social_media_res_api.utils.GetUsernameJwtTokenUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/content/sub")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionsController {

    private final GetUsernameJwtTokenUtil usernameByToken;
    private final SubscriptionsService subscriptionsService;

    @PostMapping("/{subscribe_name}")
    public ResponseEntity<?> subscribe(@PathVariable String subscribe_name, HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        return subscriptionsService.subscribe(username, subscribe_name);
    }

    @PostMapping("/friend/{subscribe_name}")
    public ResponseEntity<?> friendshipResponse(@PathVariable String subscribe_name, @RequestBody String username) {

        return ResponseEntity.ok("");
    }
}
