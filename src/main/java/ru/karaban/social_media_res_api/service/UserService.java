package ru.karaban.social_media_res_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.karaban.social_media_res_api.dto.AppError;
import ru.karaban.social_media_res_api.dto.JwtResponse;
import ru.karaban.social_media_res_api.dto.UserDto;
import ru.karaban.social_media_res_api.entity.Role;
import ru.karaban.social_media_res_api.entity.User;
import ru.karaban.social_media_res_api.exeptions.ResourceNotFoundException;
import ru.karaban.social_media_res_api.repository.UserRepository;
import ru.karaban.social_media_res_api.utils.JwtTokenUtil;
import ru.karaban.social_media_res_api.utils.MessageUtils;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService { //TODO добавить проверку на повторяющейся email или username

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtTokenUtil jwtTokenUtil;

    public ResponseEntity<?> responseUserByUsername(String username) {
        if(isUserExistsByUsername(username)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(MessageUtils.USER + username + MessageUtils.NOT_FOUND);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllByUsernameIn(List<String> usernames) {
        return userRepository.findAllByUsernameIn(usernames);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            User user = userRepository.findByUsername(username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        }catch (Exception e) {
           throw new ResourceNotFoundException(MessageUtils.USER + username + MessageUtils.NOT_FOUND);
        }
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = findUserById(id).orElseThrow(() -> new ResourceNotFoundException(MessageUtils.USER_BY_ID + id + MessageUtils.NOT_FOUND));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public boolean isUserExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<?> saveUser(UserDto userDto) {
        if (userDto.getUsername().isEmpty() || userDto.getPassword().isEmpty() || userDto.getEmail().isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), MessageUtils.REG_EMPTY), HttpStatus.BAD_REQUEST);
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
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
