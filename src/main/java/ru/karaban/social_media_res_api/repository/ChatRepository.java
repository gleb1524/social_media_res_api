package ru.karaban.social_media_res_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karaban.social_media_res_api.entity.Chat;
import ru.karaban.social_media_res_api.entity.User;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

        List<Chat> findAllBySenderOrRecipient(User sender, User recipient);

        boolean existsById(Long aLong);
        Optional<Chat> findChatById(Long id);
}
