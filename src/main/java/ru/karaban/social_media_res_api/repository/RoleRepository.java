package ru.karaban.social_media_res_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karaban.social_media_res_api.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
       List<Role> findAllByName(String name);
}
