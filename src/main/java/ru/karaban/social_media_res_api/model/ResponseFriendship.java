package ru.karaban.social_media_res_api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseFriendship {

    private String username;
    private List<String> friendship;
}
