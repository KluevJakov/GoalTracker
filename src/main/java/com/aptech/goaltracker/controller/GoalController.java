package com.aptech.goaltracker.controller;

import com.aptech.goaltracker.models.Goal;
import com.aptech.goaltracker.models.User;
import com.aptech.goaltracker.service.CustomUserDetailsService;
import com.aptech.goaltracker.service.GoalService;
import com.aptech.goaltracker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;
    @Autowired
    private CustomUserDetailsService userService;

    @GetMapping(value = "/getGoalsByUserId")
    public List<Goal> getGoalsByUserId(@RequestParam(value = "id") Long id) {
        return goalService.getGoalsByUserId(id);
    }

    @GetMapping(value = "/getGoalsByTeamId")
    public List<Goal> getGoalsByTeamId(@RequestParam(value = "id") Long id) {
        return goalService.getGoalsByTeamId(id);
    }

    @GetMapping(value = "/getGoalById")
    public Goal getGoalById(@RequestParam(value = "id") Long id) {
        return goalService.getGoalById(id);
    }

    @GetMapping(value = "/getMyGoals")
    public List<Goal> getMyGoals() {
        User currentUser = (User) userService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return goalService.getGoalsByUserId(currentUser.getId());
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestBody Goal goal) {
        try {
            User currentUser = (User) userService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            goalService.createGoal(goal, currentUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @PostMapping(value = "/createForTeam")
    public ResponseEntity<String> createForTeam(@RequestBody Goal goal) {
        try {
            goalService.createForTeam(goal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody Goal goal) {
        try {
            goalService.updateGoal(goal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) {
        try {
            goalService.deleteGoal(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }
}
