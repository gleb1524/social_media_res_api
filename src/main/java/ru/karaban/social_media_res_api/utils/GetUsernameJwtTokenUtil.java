package ru.karaban.social_media_res_api.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class GetUsernameJwtTokenUtil {

    private final JwtTokenUtil jwtTokenUtil;

    public String getUsernameByToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtTokenUtil.getUsernameFromToken(jwt);
        }
        return username;
    }
}
