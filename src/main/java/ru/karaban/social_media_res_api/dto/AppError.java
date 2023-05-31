package ru.karaban.social_media_res_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppError {
    public AppError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    private int statusCode;
    private String message;
}
