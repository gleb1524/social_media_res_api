package ru.karaban.social_media_res_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.karaban.social_media_res_api.entity.Subscriptions;
import ru.karaban.social_media_res_api.entity.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long> {

    Optional<Subscriptions> findByUser(User user);
    List<Subscriptions> findAllByFriend(User user);

    @Query(value = "select * from subscriptions sub join " +
                   "subscriptions sub2 ON sub.friend_id = sub2.user_id" +
                   " where sub.user_id = ?1 and sub.user_id = sub2.friend_id",
            nativeQuery = true)
    List<Subscriptions> findAllFriends(Long id);
    @Query(value = "select * from subscriptions sub join " +
                   "subscriptions sub2 ON sub.friend_id = sub2.user_id" +
                   " where sub.user_id = ?1 and sub.friend_id = ?2",
            nativeQuery = true)
    void deleteSubscriptionsByByFriends(Long myId, Long friendId);
}
