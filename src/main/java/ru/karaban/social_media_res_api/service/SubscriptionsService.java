package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.karaban.social_media_res_api.Model.SubscribeRequest;
import ru.karaban.social_media_res_api.entity.Subscriptions;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.repository.SubscriptionsRepository;
import ru.karaban.social_media_res_api.utils.MessageUtils;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionsService {

    private final SubscriptionsRepository repository;
    private final UserService userService;

    @Value("${friendship_request}")
    private String url;

    public ResponseEntity<?> subscribe(String username, String friendName) {

        try {
            User user = userService.findUserByUsername(username);
            User friend = userService.findUserByUsername(friendName);
            repository.save(Subscriptions.builder()
                    .user(user)
                    .friend(friend)
                    .status(false)
                    .build());
            subRequest(username, friendName);
            return ResponseEntity.ok(String.format(MessageUtils.SUBSCRIBE, friendName));
        } catch (Exception e) {
            log.error(MessageUtils.USER + friendName + MessageUtils.NOT_FOUND);
            return ResponseEntity.badRequest().body(MessageUtils.USER + friendName + MessageUtils.NOT_FOUND);
        }
    }

    private void subRequest(String username, String friendName) {
        WebClient webClient = WebClient.create();
        SubscribeRequest request = new SubscribeRequest();
        request.setUsername(username);
        webClient.post()
                .uri(url + friendName)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }

//    public ResponseEntity<?> friendshipRequest(User user, User friend) {
//
//        Subscriptions subscriptions = repository.findByUser(user)
//                .orElseThrow(() -> new ResourceNotFoundException(MessageUtils.USER + user.getUsername() + MessageUtils.NOT_FOUND));
//        if (request != null && request.equals(MessageUtils.ACCEPT)) {
//            subscriptions.setStatus(true);
//            repository.save(subscriptions);
//            log.debug(MessageUtils.ACCEPT + " " + friend.getUsername());
//            return ResponseEntity.ok(MessageUtils.ACCEPT);
//        } else if (request != null) {
//            log.debug(MessageUtils.FRIENDSHIP_REJ + " " + friend.getUsername());
//            return ResponseEntity.badRequest().body(MessageUtils.FRIENDSHIP_REJ);
//        }
//        log.error(MessageUtils.BAD_RESPONSE);
//        return ResponseEntity.badRequest().body(MessageUtils.FAILED);
//    }
}
