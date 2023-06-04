package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.karaban.social_media_res_api.dto.ResponseFriendship;
import ru.karaban.social_media_res_api.entity.Friendship;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.model.RequestFriendship;
import ru.karaban.social_media_res_api.repository.FriendshipRepository;
import ru.karaban.social_media_res_api.utils.MessageUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final SubscriptionsService subscriptionsService;
    private final UserService userService;

    public String createFriendship(String username, String friendName) {

        if (friendName.isEmpty() || username.isEmpty()) {
            throw new ResourceNotFoundException(MessageUtils.FRIEND_EMPTY + " or " + MessageUtils.USERNAME_EMPTY);
        }
        User sender = getUserByUsername(username);
        User recipient = getUserByUsername(friendName);
        friendshipRepository.save(Friendship.builder().sender(sender).recipient(recipient).build());
        return String.format(MessageUtils.OK_FRIENDSHIP, friendName);
    }

    public ResponseFriendship findAllByUser(String username) {
        if (username.isEmpty()) {
            throw new ResourceNotFoundException(MessageUtils.USERNAME_EMPTY);
        }
        ResponseFriendship response = new ResponseFriendship();
        User user = getUserByUsername(username);
        List<String> sendersFriendship = friendshipRepository.findByRecipient(user)
                .stream()
                .map(Friendship::getSender)
                .map(User::getUsername)
                .collect(Collectors.toList());
        response.setFriendship(sendersFriendship);
        response.setUsername(username);
        return response;
    }

    public String responseFriendship(String username, RequestFriendship request) {
        if (request == null) {
            throw new ResourceNotFoundException(MessageUtils.BAD_REQUEST);
        }
        String friendName = request.getUsername();
        Boolean answer = request.getAnswer();
        if (username.isEmpty() || friendName.isEmpty() || answer == null) {
            throw new ResourceNotFoundException(MessageUtils.BAD_REQUEST);
        }
        if (answer) {
            return subscriptionsService.subscribe(username, friendName);
        }
        return cancelFriendship(username, friendName);
    }

    private String cancelFriendship(String username, String friendName) {
        if (username.isEmpty() || friendName.isEmpty()) {
            throw new RuntimeException();
        }
        User user = getUserByUsername(username);
        User friend = getUserByUsername(friendName);
        friendshipRepository.deleteByRecipientAndSender(user, friend);
        return String.format(MessageUtils.REJECT_FRIENDSHIP, friendName);
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
