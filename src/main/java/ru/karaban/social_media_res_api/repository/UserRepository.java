package ru.karaban.social_media_res_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karaban.social_media_res_api.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);

    List<User> findAllByUsernameIn(List<String> usernames);

}
