package ru.karaban.social_media_res_api.exeptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){ super(message);}
}
