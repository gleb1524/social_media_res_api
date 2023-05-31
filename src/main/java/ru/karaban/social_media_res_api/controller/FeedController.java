package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karaban.social_media_res_api.dto.AppError;
import ru.karaban.social_media_res_api.dto.PostDto;
import ru.karaban.social_media_res_api.service.FeedService;


@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/post")
    public ResponseEntity<?> savePost(@RequestBody PostDto postDto) {
        if(postDto.getTitle().isEmpty() || postDto.getImage()==null || postDto.getText().isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "The post must have a title, text and content"), HttpStatus.BAD_REQUEST);
        }
        feedService.savePost(postDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<?> getFeed() {
        return ResponseEntity.ok().body(feedService.getFeed());
    }
}
