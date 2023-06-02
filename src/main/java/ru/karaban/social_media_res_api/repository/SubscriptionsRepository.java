package ru.karaban.social_media_res_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karaban.social_media_res_api.entity.Subscriptions;
import ru.karaban.social_media_res_api.entity.User;

import java.util.Optional;

public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long> {

    Optional<Subscriptions> findByUser(User user);
}
