package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.dto.ResponseFriendship;
import ru.karaban.social_media_res_api.entity.Subscriptions;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.repository.SubscriptionsRepository;
import ru.karaban.social_media_res_api.utils.MessageUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionsService {

    private final SubscriptionsRepository repository;
    private final UserService userService;

    public String subscribe(String username, String friendName) {

        try {
            User user = userService.findUserByUsername(username);
            User friend = userService.findUserByUsername(friendName);
            repository.save(Subscriptions.builder()
                    .user(user)
                    .friend(friend)
                    .build());
            return String.format(MessageUtils.SUBSCRIBE, friendName);
        } catch (Exception e) {
            log.error(MessageUtils.USER + friendName + MessageUtils.NOT_FOUND);
            return MessageUtils.USER + friendName + MessageUtils.NOT_FOUND;
        }
    }

    public ResponseFriendship getSubscribes(String username) {

        try {
            User user = userService.findUserByUsername(username);
            List<Subscriptions> subscriptions = repository.findAllByFriend(user);
            ResponseFriendship response = new ResponseFriendship();
            List<String> subscribers = subscriptions.stream()
                    .map(Subscriptions::getUser)
                    .map(User::getUsername).collect(Collectors.toList());
            response.setFriendship(subscribers);
            response.setUsername(username);
            log.info("Return all friendship " + username);
            return response;
        } catch (Exception e) {
            log.error(MessageUtils.USER + username + MessageUtils.NOT_FOUND);
            throw  new ResourceNotFoundException(e.getMessage());
        }
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
