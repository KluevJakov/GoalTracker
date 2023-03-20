package com.aptech.goaltracker.service;

import com.aptech.goaltracker.models.Goal;
import com.aptech.goaltracker.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public void createGoal(Goal goal) {
        goalRepository.save(goal);
    }

    public List<Goal> getGoalsByUserId(Long id) {
        return goalRepository.getGoalsByUserId(id);
    }

    public List<Goal> getGoals() {
        return goalRepository.findAll();
    }
}
