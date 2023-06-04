package ru.karaban.social_media_res_api.utils;

public interface MessageUtils {

    String FAILED = "Failed";
    String FAIL_AUTH = "Incorrect username or password";
    String POST_CREATED = "Post created";
    String POST_UPDATED = "Post updated";
    String REG_NOT_UNIQUE = "Failed registration! Username [%s] or email [%s] is already taken";
    String USER = "User with username: ";
    String USER_BY_ID = "User by id: ";
    String USER_NEW = "Save new User(username: %s, email: %s)!";
    String NOT_FOUND = " not found";
    String FILE_EMPTY = "Failed. File is empty";
    String BAD_UPLOAD = "Failed to upload file";
    String BAD_GEN_HASH = "Failed to generate hash";
    String BAD_MEDIA_REQUEST = "Bad request. The file, title and text cant be empty";
    String SUBSCRIBE = "Subscribe to %s successfully";
    String UNSUBSCRIBE = "Unsubscribe to %s successfully";
    String ACCEPT = "Accepted friendship";
    String FRIENDSHIP_REJ = "Friendship request rejected";
    String BAD_RESPONSE = "Response is empty";
    String BAD_REQUEST = "Bad request";
    String REJECT_FRIENDSHIP = "Friendship from the user %s was successfully rejected";
    String OK_FRIENDSHIP = "Friendship to user %s send";
    String USERNAME_EMPTY = "Username is empty";
    String FRIEND_EMPTY = "Friend is empty";
    String DELETE_OK = "Deletion completed successfully";
    String DELETE_BAD = "Deletion failed";
}
