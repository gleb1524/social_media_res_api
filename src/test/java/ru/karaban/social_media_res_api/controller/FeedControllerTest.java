package ru.karaban.social_media_res_api.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import ru.karaban.social_media_res_api.dto.PostDto;
import ru.karaban.social_media_res_api.model.ResponsePost;
import ru.karaban.social_media_res_api.service.FeedService;
import ru.karaban.social_media_res_api.utils.MessageUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class FeedControllerTest {

    @Autowired
    private FeedService feedService;

    @Test
    void getFeed() {
        Path path = Paths.get("resources/3A/00/1A6F4F95E5B9EEAE4B2F626C313C01D3/image.txt");
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MockMultipartFile file = new MockMultipartFile("avatar", "image.txt", "text/plain", content);
        feedService.savePost(PostDto.builder().id(1L).text("text").title("title").file(file).build(), "friend");
        List<ResponsePost> feed = feedService.getFeed(0, 10, "updatedAt", "user");
        Assertions.assertNotNull(feed);
        Assertions.assertEquals(feed.get(0).getTitle(),"title");
        Assertions.assertEquals(feed.get(0).getText(),"text");
    }

    @Test
    void deletePost() {

        Path path = Paths.get("resources/3A/00/1A6F4F95E5B9EEAE4B2F626C313C01D3/image.txt");
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MockMultipartFile file = new MockMultipartFile("avatar", "image.txt", "text/plain", content);
        beforeDelete();
        String deletePost = feedService.deletePost(PostDto.builder().id(1L).text("text").title("title").file(file).build(), "friend");
        Assertions.assertEquals(MessageUtils.DELETE_OK, deletePost);
        afterDelete();
    }

    @SneakyThrows
    private void beforeDelete() {
        File source = new File("resources/3A/00/1A6F4F95E5B9EEAE4B2F626C313C01D3/image.txt");
        File dest = new File("resources/3A/00/image.txt");
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @SneakyThrows
    private void afterDelete() {
        File source = new File("resources/3A/00/image.txt");
        File dest = new File("resources/3A/00/1A6F4F95E5B9EEAE4B2F626C313C01D3/image.txt");
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}