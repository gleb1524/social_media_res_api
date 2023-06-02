package ru.karaban.social_media_res_api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFriendship {

    private String username;
    private Boolean answer;
}
