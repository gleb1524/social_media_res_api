package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;
}
