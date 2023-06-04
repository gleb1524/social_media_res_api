package ru.karaban.social_media_res_api.model;

import lombok.*;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePost {

    private Long id;
    private String title;
    private String text;
}

