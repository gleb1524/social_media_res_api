package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karaban.social_media_res_api.dto.PostDto;
import ru.karaban.social_media_res_api.entity.Post;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.repository.PostRepository;
import ru.karaban.social_media_res_api.utils.MessageUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final ImageService imageService;

    @Transactional
    public String savePost(PostDto postDto, String username) {

        String response = imageService.mediaProcessing(postDto.getFile(), username);
        if (response.startsWith(MessageUtils.FAILED)) {
            throw new ResourceNotFoundException(response);
        }
        User user = getCurrentUser(username);
        postRepository.save(Post.builder()
                .title(postDto.getTitle())
                .text(postDto.getText())
                .imagePath(response)
                .user(user)
                .build());
        return MessageUtils.POST_CREATED;
    }

//    public List<PostDto> getFeed() {
//        User currentUser = getCurrentUser();
//        List<String> subscriptionsUsername = currentUser.getSubscriptions()
//                .stream().map(Subscriptions::getSubscribe_username).collect(Collectors.toList());
//        List<User> subscriptions = userService.findAllByUsernameIn(subscriptionsUsername);
//        subscriptions.add(currentUser);
//        return postRepository.findByUserInOrderByCreatedAtDesc(subscriptions)
//                .stream()
//                .map(converter::entityToDtoPost)
//                .collect(Collectors.toList());
//    }

    private User getCurrentUser(String username) {
        try{
            return userService.findUserByUsername(username);
        } catch (Exception e) {
           throw new ResourceNotFoundException(MessageUtils.USER + username + MessageUtils.NOT_FOUND);
        }
    }

//    public String deletePost(PostDto postDto, String username) {
//        User user = getCurrentUser(username);
//        postRepository.delete(Post.builder()
//                .title(postDto.getTitle())
//                .text(postDto.getText())
//                .imagePath(response)
//                .user(user)
//                .build());
//    }
}

