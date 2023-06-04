package ru.karaban.social_media_res_api.model;

import lombok.*;

import java.io.File;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    private Long id;
    private String chatPath;
    private String senderName;
    private String recipientName;
}
