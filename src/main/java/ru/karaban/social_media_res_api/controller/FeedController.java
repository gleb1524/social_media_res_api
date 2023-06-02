package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karaban.social_media_res_api.dto.PostDto;
import ru.karaban.social_media_res_api.service.FeedService;
import ru.karaban.social_media_res_api.service.ImageService;
import ru.karaban.social_media_res_api.utils.GetUsernameJwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/content/post")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final ImageService imageService;
    private final GetUsernameJwtTokenUtil usernameByToken;

    @PostMapping()
    public ResponseEntity<String> savePost(@Valid @ModelAttribute PostDto postDto, HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = feedService.savePost(postDto, username);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> deletePost(@Valid @ModelAttribute PostDto postDto, HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = feedService.deletePost(postDto, username);
        return ResponseEntity.ok(response);
    }
}
