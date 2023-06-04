package ru.karaban.social_media_res_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.karaban.social_media_res_api.service.ImageService;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/content/img")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
//    @GetMapping("/{id}")
//    public ResponseEntity<Resource> getImage(@PathVariable int id) {
//
//            try {
//                Path file = Paths.get(imagePath);
//                Resource resource = new UrlResource(file.toUri());
//                if (resource.exists() || resource.isReadable()) {
//                    return ResponseEntity.ok()
//                            .contentType(MediaType.IMAGE_JPEG)
//                            .body(resource);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return ResponseEntity.notFound().build();
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {

        Resource resource = imageService.getImage(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }
}
