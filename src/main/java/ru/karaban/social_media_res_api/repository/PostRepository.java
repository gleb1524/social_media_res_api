package ru.karaban.social_media_res_api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.karaban.social_media_res_api.entity.Post;
import ru.karaban.social_media_res_api.entity.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);

}
