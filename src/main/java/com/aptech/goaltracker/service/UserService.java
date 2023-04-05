package com.aptech.goaltracker.service;

import com.aptech.goaltracker.models.Role;
import com.aptech.goaltracker.models.User;
import com.aptech.goaltracker.models.dto.UserDto;
import com.aptech.goaltracker.repository.RoleRepository;
import com.aptech.goaltracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser (User user) {
        if (StringUtils.isEmptyOrWhitespace(user.getLogin())) {
            throw new IllegalArgumentException("Логин не должен быть пустым");
        }
        if (StringUtils.isEmptyOrWhitespace(user.getPassword())) {
            throw new IllegalArgumentException("Пароль не должен быть пустым");
        }
        if (user.getPassword().length() < 8 || user.getPassword().length() > 20) {
            throw new IllegalArgumentException("Длина пароля 8-20 символов");
        }
        if (user.getLogin().length() < 8 || user.getLogin().length() > 20) {
            throw new IllegalArgumentException("Длина логина 8-20 символов");
        }
        if (userRepository.countUserByLogin(user.getLogin()) != 0) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует");
        }
        if (!user.getPassword().equals(user.getPasswordAccept())) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findById(1L).get();
        user.setRole(Collections.singleton(role));

        userRepository.save(user);
    }

    public List<UserDto> searchUsersByLogin(String login, Long myId) {
        List<UserDto> resultList = new ArrayList<>();

        userRepository.searchUsersByLogin(login).stream().limit(5).forEach(e -> {
            if (!e.getId().equals(myId)) {
                resultList.add(new UserDto(e.getId(), e.getLogin()));
            }
        });

        return resultList;
    }
}
