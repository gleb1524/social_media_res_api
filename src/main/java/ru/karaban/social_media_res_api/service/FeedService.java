package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.converter.ConverterEntityAndDto;
import ru.karaban.social_media_res_api.dto.PostDto;
import ru.karaban.social_media_res_api.dto.UserDto;
import ru.karaban.social_media_res_api.entity.Image;
import ru.karaban.social_media_res_api.entity.Post;
import ru.karaban.social_media_res_api.entity.Subscriptions;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.repository.PostRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final ConverterEntityAndDto converter;

    public void savePost(PostDto postDto) {
        Image image = imageService.saveImage(postDto);
        User user = getCurrentUser();
        postRepository.save(Post.builder()
                .title(postDto.getTitle())
                .text(postDto.getText())
                .image(image)
                .user(user)
                .build());
        log.info(String.format("User(id: %d, username: %s) save new post", user.getId(), user.getUsername()));
    }

    public List<PostDto> getFeed() {
        User currentUser = getCurrentUser();
        List<String> subscriptionsUsername = currentUser.getSubscriptions()
                .stream().map(Subscriptions::getSubscribe_username).collect(Collectors.toList());
        List<User> subscriptions = userService.findAllByUsernameIn(subscriptionsUsername);
        subscriptions.add(currentUser);
        return postRepository.findByUserInOrderByCreatedAtDesc(subscriptions)
                .stream()
                .map(converter::entityToDtoPost)
                .collect(Collectors.toList());
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found."));
    }
}

