package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.karaban.social_media_res_api.utils.MessageUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final MediaProcessingService mediaProcessingService;

    public String mediaProcessing(MultipartFile file, String username) {
        try {
            byte[] bytes = file.getBytes();
            String accountHash = mediaProcessingService.getAccountHash(username);
            String filename = file.getOriginalFilename();
            Path path = mediaProcessingService.getFilePath(filename, accountHash);
            Path filePath = Paths.get(path.toString(), filename);
            Files.write(filePath, bytes);
            return  path.toString();
        } catch (IOException e) {
            return MessageUtils.BAD_UPLOAD;
        }
    }
}
