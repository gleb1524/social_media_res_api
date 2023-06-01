package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.karaban.social_media_res_api.dto.AppError;
import ru.karaban.social_media_res_api.dto.JwtResponse;
import ru.karaban.social_media_res_api.dto.UserDto;
import ru.karaban.social_media_res_api.service.UserService;
import ru.karaban.social_media_res_api.utils.JwtTokenUtil;
import ru.karaban.social_media_res_api.utils.MessageUtils;

@RestController
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegController {

    @Value("${jwt.secret}")
    private String secret;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping()
    public ResponseEntity<?> registration(@RequestBody UserDto userDto) {
        if(userDto.getUsername().isEmpty() || userDto.getPassword().isEmpty() || userDto.getEmail().isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), MessageUtils.REG_EMPTY), HttpStatus.BAD_REQUEST);
        }
        String salt = BCrypt.gensalt();
        String password = BCrypt.hashpw(userDto.getPassword(), salt);
        userService.saveUser(userDto.getUsername(), password, userDto.getEmail());
        UserDetails userDetails = userService.loadUserByUsername(userDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
