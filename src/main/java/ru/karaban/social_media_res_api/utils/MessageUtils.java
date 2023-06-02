package ru.karaban.social_media_res_api.utils;

public interface MessageUtils {

    String FAILED = "Failed";
    String FAIL_AUTH = "Incorrect username or password";
    String POST_CREATED = "Post created";
    String REG_EMPTY = "Username, email or password can't be empty";
    String USER = "User with username: ";
    String USER_BY_ID = "User by id: ";
    String USER_NEW = "Save new User(username: %s, email: %s)!";
    String NOT_FOUND = " not found";
    String FILE_EMPTY = "Failed. File is empty";
    String BAD_UPLOAD = "Failed to upload file";
    String BAD_GEN_HASH = "Failed to generate hash";
    String BAD_MEDIA_REQUEST = "Bad request. The file, title and text cant be empty";
    String SUBSCRIBE = "Subscribe to %s successfully";
    String ACCEPT = "Accepted friendship";
    String FRIENDSHIP_REJ = "Friendship request rejected";
    String BAD_RESPONSE = "Response is empty";
}
