package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.dto.UserDto;
import ru.karaban.social_media_res_api.entity.Role;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.repository.UserRepository;
import ru.karaban.social_media_res_api.utils.JwtTokenUtil;
import ru.karaban.social_media_res_api.utils.MessageUtils;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        } catch (Exception e) {
            throw new ResourceNotFoundException(MessageUtils.USER + username + MessageUtils.NOT_FOUND);
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String saveUser(UserDto userDto) {
        if(isUserDataUniq(userDto)){
            log.error(String.format(MessageUtils.REG_NOT_UNIQUE, userDto.getUsername(), userDto.getEmail()));
            throw new ResourceNotFoundException(String.format(MessageUtils.REG_NOT_UNIQUE, userDto.getUsername(), userDto.getEmail()));
        }
        String salt = BCrypt.gensalt();
        String password = BCrypt.hashpw(userDto.getPassword(), salt);
        List<Role> roleUser = roleService.findAllByName("ROLE_USER");
        userRepository.save(User.builder()
                .username(userDto.getUsername())
                .password(password)
                .email(userDto.getEmail())
                .roles(roleUser)
                .build());
        UserDetails userDetails = loadUserByUsername(userDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        log.info(String.format(MessageUtils.USER_NEW, userDto.getUsername(), userDto.getEmail()));
        return token;
    }

    private boolean isUserDataUniq (UserDto userDto) {
        return userRepository.existsByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
    }
}
