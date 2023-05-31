package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.dto.PostDto;
import ru.karaban.social_media_res_api.entity.Image;
import ru.karaban.social_media_res_api.repository.ImageRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;

    public Image saveImage(PostDto postDto) {
        log.info("Save image with title: " + postDto.getTitle());
        return imageRepository.save(Image.builder().data(postDto.getImage()).build());
    }
}
