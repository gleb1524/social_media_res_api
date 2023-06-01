package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karaban.social_media_res_api.dto.PostDto;
import ru.karaban.social_media_res_api.service.FeedService;
import ru.karaban.social_media_res_api.service.ImageService;
import ru.karaban.social_media_res_api.utils.GetUsernameJwtTokenUtil;
import ru.karaban.social_media_res_api.utils.MessageUtils;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final ImageService imageService;
    private final GetUsernameJwtTokenUtil usernameByToken;

    @PostMapping("/post")

    public ResponseEntity<String> savePost(@ModelAttribute PostDto postDto, HttpServletRequest request) {
        if (postDto.getTitle().isEmpty() || postDto.getText().isEmpty() || postDto.getFile().isEmpty()) {
            return ResponseEntity.badRequest().body(MessageUtils.BAD_MEDIA_REQUEST);
        }
        String username = usernameByToken.getUsernameByToken(request);
        String response = imageService.mediaProcessing(postDto.getFile(), username);
        if (response.startsWith(MessageUtils.FAILED)) {
            return ResponseEntity.badRequest().body(response);
        } else {
            feedService.savePost(postDto, response);
            return ResponseEntity.ok().body(MessageUtils.POST_CREATED);
        }
    }
}
