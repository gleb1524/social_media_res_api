package ru.karaban.social_media_res_api.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMessage {

    private Long chatId;
    private String sender;
    private String recipient;
    private String message;
}
