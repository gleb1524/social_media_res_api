package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.entity.Post;
import ru.karaban.social_media_res_api.entity.Subscriptions;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.repository.PostRepository;
import ru.karaban.social_media_res_api.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<Post> getFeed() {
        User currentUser = getCurrentUser();
        List<String> subscriptionsUsername = currentUser.getSubscriptions()
                .stream().map(Subscriptions::getSubscribe_username).collect(Collectors.toList());
        List<User> subscriptions = userRepository.findByUsernameIn(subscriptionsUsername);
        subscriptions.add(currentUser);
        return postRepository.findByUserInOrderByCreatedAtDesc(subscriptions);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с именем " + username + " не найден."));
    }
}

