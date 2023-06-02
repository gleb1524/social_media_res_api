package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.karaban.social_media_res_api.dto.UserDto;
import ru.karaban.social_media_res_api.service.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<?> registration(@Valid @RequestBody UserDto userDto) {

        String response = userService.saveUser(userDto);
        return ResponseEntity.ok(response);
    }
}
