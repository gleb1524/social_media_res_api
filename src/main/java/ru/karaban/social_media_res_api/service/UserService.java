package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.entity.Role;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.repository.UserRepository;
import ru.karaban.social_media_res_api.utils.MessageUtils;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllByUsernameIn(List<String> usernames) {
       return userRepository.findAllByUsernameIn(usernames);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(MessageUtils.USER + username + MessageUtils.NOT_FOUND));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = findUserById(id).orElseThrow(() -> new ResourceNotFoundException(MessageUtils.USER_BY_ID + id + MessageUtils.NOT_FOUND));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void saveUser(String username, String password, String email) {
        List<Role> roleUser = roleService.findAllByName("ROLE_USER");
        userRepository.save(User.builder()
                .username(username)
                .password(password)
                .email(email)
                .roles(roleUser)
                .build());
        log.info(String.format(MessageUtils.USER_NEW, username, email) );
    }
}
