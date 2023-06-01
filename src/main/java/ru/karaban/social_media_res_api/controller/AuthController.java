package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.karaban.social_media_res_api.dto.JwtRequest;
import ru.karaban.social_media_res_api.dto.JwtResponse;
import ru.karaban.social_media_res_api.service.UserService;
import ru.karaban.social_media_res_api.utils.JwtTokenUtil;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping()
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
