package ru.karaban.social_media_res_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karaban.social_media_res_api.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); //TODO подумать над возможностью убрать Options,
                                                    // если в дальнейшем искать пользователя будем только из секьюрити контекста
    List<User> findByUsernameIn(List<String> usernames);

}
