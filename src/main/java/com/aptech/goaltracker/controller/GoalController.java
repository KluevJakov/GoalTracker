package com.aptech.goaltracker.controller;

import com.aptech.goaltracker.models.Goal;
import com.aptech.goaltracker.service.GoalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping(value = "/getGoalsByUserId")
    public List<Goal> getGoalsByUserId(@RequestParam(value = "id") Long id) {
        log.warn("getGoalsByUserId = " + id);
        return goalService.getGoalsByUserId(id);
    }
}
