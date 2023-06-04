package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.entity.Chat;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.model.ChatDto;
import ru.karaban.social_media_res_api.model.SendMessage;
import ru.karaban.social_media_res_api.repository.ChatRepository;
import ru.karaban.social_media_res_api.utils.MediaProcessingUtils;
import ru.karaban.social_media_res_api.utils.MessageUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;
    private final MediaProcessingUtils mediaProcessingUtils;
    private final SubscriptionsService subscriptionsService;

    public void saveChat(String username,  SendMessage message) {

        User user = getCurrentUser(username);
        User friend = getCurrentUser(message.getRecipient());

        if(message.getChatId()==null){
            String accountHash = mediaProcessingUtils.getAccountHash(username);
            String fileName = "chat_with_" + message.getRecipient() + ".txt";
            Path filePath = mediaProcessingUtils.getFilePath(fileName, accountHash);
            Path fileChatPath = Paths.get(filePath.toString(), fileName);
            recordMessage(fileChatPath.toString(), username, message.getRecipient(), message.getMessage());
            chatRepository.save(Chat.builder().sender(user).recipient(friend).chatPath(fileChatPath.toString()).build());
        } else {
            String fileChatPath =
                    chatRepository.findChatById(message.getChatId())
                            .orElseThrow(()-> new ResourceNotFoundException(MessageUtils.NOT_FOUND)).getChatPath();
            recordMessage(fileChatPath, username, message.getRecipient(), message.getMessage());
            chatRepository.save(Chat.builder().id(message.getChatId()).sender(user).recipient(friend).chatPath(fileChatPath).build());
        }
    }

    private void recordMessage(String fileChatPath, String username, String friendName, String message) {
        if(subscriptionsService.isFriend(username, friendName)){
            try{
                File file = new File(fileChatPath);
                BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
                output.append(String.format("[from : %s to : %s ]:\n %s \n", username, friendName, message));
                output.close();
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        } else {
            throw new ResourceNotFoundException("You can only send messages to friends");
        }
    }


    public List<ChatDto> getMessages(String username) {

        User user = getCurrentUser(username);

        List<Chat> chats = chatRepository.findAllBySenderOrRecipient(user, user);
       return chats.stream().map(chat -> ChatDto.builder()
                .id(chat.getId())
                .senderName(chat.getSender().getUsername())
                .recipientName(chat.getRecipient().getUsername())
                .chatPath(chat.getChatPath())
                .build()).collect(Collectors.toList());
    }

    private User getCurrentUser(String username) {
        try {
            return userService.findUserByUsername(username);
        } catch (Exception e) {
            throw new ResourceNotFoundException(MessageUtils.USER + username + MessageUtils.NOT_FOUND + " or " + e.getMessage());
        }
    }
}
