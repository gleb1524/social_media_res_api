package ru.karaban.social_media_res_api.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {
    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable int id) {
        String imagePath = getImagePath(id);
        if (imagePath != null) {
            try {
                Path file = Paths.get(imagePath);
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(resource);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.notFound().build();
    }

    private String getImagePath(int id) {
        return "C:/Users/gleb1/Desktop/photo_5453992258828356025_y.jpg";
    }
}
