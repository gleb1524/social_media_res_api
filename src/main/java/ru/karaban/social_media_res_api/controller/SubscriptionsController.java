package ru.karaban.social_media_res_api.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karaban.social_media_res_api.model.ResponseFriendship;
import ru.karaban.social_media_res_api.service.SubscriptionsService;
import ru.karaban.social_media_res_api.utils.GetUsernameJwtTokenUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/content/sub")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionsController {

    private final GetUsernameJwtTokenUtil usernameByToken;
    private final SubscriptionsService subscriptionsService;

    @ApiOperation(value = "Subscribe to user by username", response = String.class)
    @PostMapping("/{subscribe_name}")
    public ResponseEntity<?> subscribe(@PathVariable String subscribe_name, HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = subscriptionsService.subscribe(username, subscribe_name);
        return ResponseEntity.ok(response);

    }
    @ApiOperation(value = "Unsubscribe ", response = String.class)
    @DeleteMapping("/{subscribe_name}")
    public ResponseEntity<?> unsubscribe(@PathVariable String subscribe_name, HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = subscriptionsService.unsubscribe(username, subscribe_name);
        return ResponseEntity.ok(response);

    }

    @ApiOperation(value = "Get all subscribes", response = ResponseFriendship.class)
    @GetMapping()
    @ResponseBody
    public ResponseEntity<?> friendshipResponse(HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        ResponseFriendship response = subscriptionsService.getSubscribes(username);
        return ResponseEntity.ok(response);
    }
}
