package ru.karaban.social_media_res_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karaban.social_media_res_api.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
