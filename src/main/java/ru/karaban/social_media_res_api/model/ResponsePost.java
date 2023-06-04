package ru.karaban.social_media_res_api.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePost {

    private Long id;
    private String title;
    private String text;
}

