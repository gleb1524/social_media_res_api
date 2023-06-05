package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.model.ResponseFriendship;
import ru.karaban.social_media_res_api.entity.Subscriptions;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.repository.SubscriptionsRepository;
import ru.karaban.social_media_res_api.utils.MessageUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionsService {

    private final SubscriptionsRepository repository;
    private final UserService userService;

    public String subscribe(String username, String friendName) {

        User user = getUserByUsername(username);
        User friend = getUserByUsername(friendName);
        repository.save(Subscriptions.builder()
                .user(user)
                .friend(friend)
                .build());
        return String.format(MessageUtils.SUBSCRIBE, friendName);
    }

    public String unsubscribe(String username, String subscribeName) {

        User user = getUserByUsername(username);
        User friend = getUserByUsername(subscribeName);
        repository.deleteSubscriptionsByUserIdAndFriendId(user.getId(), friend.getId());
        return String.format(MessageUtils.UNSUBSCRIBE, subscribeName);
    }

    public ResponseFriendship getSubscribes(String subscriberName) {

        User user = getUserByUsername(subscriberName);
        List<Subscriptions> subscriptions = repository.findAllByFriend(user);
        ResponseFriendship response = new ResponseFriendship();
        List<String> subscribers = subscriptions.stream()
                .map(Subscriptions::getUser)
                .map(User::getUsername).collect(Collectors.toList());
        response.setFriendship(subscribers);
        response.setUsername(subscriberName);
        log.info("Return all subscribes " + subscriberName);
        return response;
    }

    public List<Subscriptions> getMySubscriptions(String username) {
        User user = getUserByUsername(username);
        return repository.findAllByUser(user);
    }

    private List<String> getFriends(String username) {

        User user = getUserByUsername(username);
        Long id = user.getId();
        List<Subscriptions> subscriptions = repository.findAllFriends(id);
        List<String> friendsName = subscriptions.stream()
                .map(Subscriptions::getFriend)
                .map(User::getUsername).collect(Collectors.toList());
        return friendsName;
    }

    public boolean isFriend(String username, String friendName) {

       return getFriends(username).contains(friendName);
    }

    private User getUserByUsername(String username) {
        try {
            return userService.findUserByUsername(username);
        } catch (Exception e) {
            log.error(String.format("%s%s or %s", MessageUtils.USER, MessageUtils.NOT_FOUND, Arrays.toString(e.getStackTrace())));
            throw new ResourceNotFoundException(MessageUtils.USER + username + MessageUtils.NOT_FOUND);
        }
    }

}
