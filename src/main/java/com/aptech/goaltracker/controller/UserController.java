package com.aptech.goaltracker.controller;

import com.aptech.goaltracker.models.User;
import com.aptech.goaltracker.service.UserService;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
}
