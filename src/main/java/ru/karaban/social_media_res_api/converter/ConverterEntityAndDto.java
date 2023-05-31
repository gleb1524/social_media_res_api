package ru.karaban.social_media_res_api.converter;

import org.springframework.stereotype.Component;
import ru.karaban.social_media_res_api.dto.PostDto;
import ru.karaban.social_media_res_api.dto.UserDto;
import ru.karaban.social_media_res_api.entity.Post;
import ru.karaban.social_media_res_api.entity.User;

@Component
public class ConverterEntityAndDto {
    public UserDto entityToDtoUser(User entity) {
        return UserDto.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .build();
    }

    public PostDto entityToDtoPost(Post entity) {
        return PostDto.builder()
                .title(entity.getTitle())
                .text(entity.getText())
                .image(entity.getImage().getData())
                .build();
    }
}
