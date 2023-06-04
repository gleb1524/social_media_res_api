package ru.karaban.social_media_res_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karaban.social_media_res_api.entity.Friendship;
import ru.karaban.social_media_res_api.entity.User;
import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findByRecipient(User recipient);

    void deleteBySender(User sender);

    void deleteByRecipientAndSender(User recipient, User sender);
}
