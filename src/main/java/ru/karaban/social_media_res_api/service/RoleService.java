package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.entity.Role;
import ru.karaban.social_media_res_api.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public List<Role> findAllByName(String name) {
        return repository.findAllByName(name);
    }
}
