package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import ru.karaban.social_media_res_api.dto.PostDto;
import ru.karaban.social_media_res_api.model.ResponsePost;
import ru.karaban.social_media_res_api.service.FeedService;
import ru.karaban.social_media_res_api.service.ImageService;
import ru.karaban.social_media_res_api.utils.GetUsernameJwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RestController
@RequestMapping("/api/v1/content/post")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final GetUsernameJwtTokenUtil usernameByToken;

    @PostMapping()
    public ResponseEntity<String> savePost(@Valid @ModelAttribute PostDto postDto, HttpServletRequest request) {

        String username = usernameByToken.getUsernameByToken(request);
        String response = feedService.savePost(postDto, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getFeed(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        String username = usernameByToken.getUsernameByToken(request);
        List<ResponsePost> response = feedService.getFeed(pageNo, pageSize, sortBy, username);
        return ResponseEntity.ok().body(response);
    }

//    public ResponseEntity<String> deletePost(@Valid @ModelAttribute PostDto postDto, HttpServletRequest request) {
//
//        String username = usernameByToken.getUsernameByToken(request);
//        String response = feedService.deletePost(postDto, username);
//        return ResponseEntity.ok(response);
//    }
}
