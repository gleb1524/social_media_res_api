package ru.karaban.social_media_res_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.karaban.social_media_res_api.entity.Post;
import ru.karaban.social_media_res_api.entity.User;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByIdIn(List<Long> idList, Pageable paging);

    Optional<Post> getPostByIdAndUser(Long id, User user);

    Optional<Post> getPostById(Long id);
}
