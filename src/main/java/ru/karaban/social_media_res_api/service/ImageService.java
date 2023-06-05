package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.karaban.social_media_res_api.entity.Post;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.repository.PostRepository;
import ru.karaban.social_media_res_api.utils.MediaProcessingUtils;
import ru.karaban.social_media_res_api.utils.MessageUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final MediaProcessingUtils mediaProcessingUtils;
    private final PostRepository postRepository;


    public String mediaProcessing(MultipartFile file, String username) {
        try {
            byte[] bytes = file.getBytes();
            String accountHash = mediaProcessingUtils.getAccountHash(username);
            String filename = file.getOriginalFilename();
            Path path = mediaProcessingUtils.getFilePath(filename, accountHash);
            Path filePath = Paths.get(path.toString(), filename);
            Files.write(filePath, bytes);
            log.debug("Image save uri: " + filePath);
            return filePath.toString();
        } catch (IOException e) {
            return MessageUtils.BAD_UPLOAD;
        }
    }

    public Resource getImage(Long id) {

        Post post = postRepository.getPostById(id).orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NOT_FOUND));
        String imagePath = post.getImagePath();
        try {
            Path file = Paths.get(imagePath);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                log.debug("Get image " + imagePath);
                return resource;
            } else {
                log.error("Image by id " + id + MessageUtils.NOT_FOUND);
                throw new ResourceNotFoundException(MessageUtils.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException(MessageUtils.NOT_FOUND);
        }
    }

    public boolean deleteImg(Long id, User user) {

        try {
            Post post = postRepository.getPostByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException(MessageUtils.NOT_FOUND));
            String imagePath = post.getImagePath();
            File file = new File(imagePath);
            log.debug(MessageUtils.DELETE_OK + "image by id: " + id);
            return Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
