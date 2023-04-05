package com.aptech.goaltracker.controller;

import com.aptech.goaltracker.models.User;
import com.aptech.goaltracker.models.dto.UserDto;
import com.aptech.goaltracker.service.CustomUserDetailsService;
import com.aptech.goaltracker.service.UserService;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService userServiceImpl;

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestBody User user) {
        try {
            userService.registerUser(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @GetMapping(value = "/checkAuth")
    public ResponseEntity<String> checkAuth() {
        Collection<?> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (roles.stream().anyMatch(e -> e.getClass().equals(SimpleGrantedAuthority.class))) {
            return ResponseEntity.ok(Boolean.toString(false));
        }
        return ResponseEntity.ok(Boolean.toString(true));
    }

    @GetMapping(value = "/getByLogin")
    public List<UserDto> getByLogin(@RequestParam("login") String login) {
        User currentUser = (User) userServiceImpl.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return userService.searchUsersByLogin(login, currentUser.getId());
    }
}
