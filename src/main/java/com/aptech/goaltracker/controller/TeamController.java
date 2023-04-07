package com.aptech.goaltracker.controller;

import com.aptech.goaltracker.models.Goal;
import com.aptech.goaltracker.models.Team;
import com.aptech.goaltracker.models.User;
import com.aptech.goaltracker.service.CustomUserDetailsService;
import com.aptech.goaltracker.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private CustomUserDetailsService userService;

    @GetMapping(value = "/getTeamById")
    public Team getTeamById(@RequestParam(value = "id") Long id) {
        return teamService.getTeamById(id);
    }

    @GetMapping(value = "/getTeamByInitiatorId")
    public List<Team> getTeamByInitiatorId() {
        User currentUser = (User) userService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return teamService.getTeamByInitiatorId(currentUser.getId());
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestBody Team team) {
        try {
            User currentUser = (User) userService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            teamService.createTeam(team, currentUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody Team team) {
        try {
            teamService.updateTeam(team);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) {
        try {
            teamService.deleteTeam(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }
}
