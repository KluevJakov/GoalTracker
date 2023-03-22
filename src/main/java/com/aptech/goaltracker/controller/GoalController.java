package com.aptech.goaltracker.controller;

import com.aptech.goaltracker.models.Goal;
import com.aptech.goaltracker.models.MsgDto;
import com.aptech.goaltracker.service.GoalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping(value = "/getGoalsByUserId")
    public List<Goal> getGoalsByUserId(@RequestParam(value = "id") Long id) {
        //log.warn("getGoalsByUserId = " + id);
        return goalService.getGoalsByUserId(id);
    }

    @PostMapping(value = "/testPostMapping")
    public void testPostMapping(@RequestBody MsgDto message) {
        System.out.println("Я получил с фронтенда: " + message.getMessage());
    }
}
