package ru.karaban.social_media_res_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/content/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> testTokenValidation() {
        return ResponseEntity.ok().build();
    }
}
