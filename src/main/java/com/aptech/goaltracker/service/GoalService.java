package com.aptech.goaltracker.service;

import com.aptech.goaltracker.models.Goal;
import com.aptech.goaltracker.models.User;
import com.aptech.goaltracker.repository.GoalRepository;
import com.aptech.goaltracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createGoal(Goal goal, User user) {
        goal.setSuccess(false);
        Goal createdGoal = goalRepository.save(goal);
        if (user.getGoals() == null) {
            user.setGoals(new ArrayList<>());
        }
        user.getGoals().add(createdGoal);
        userRepository.save(user);
    }

    public void updateGoal(Goal goal) {
        goal.setSuccess(false);
        goalRepository.save(goal);
    }

    public List<Goal> getGoalsByUserId(Long id) {
        return goalRepository.getGoalsByUserId(id);
    }

    public Goal getGoalById(Long id) {
        return goalRepository.findById(id).get();
    }

    public List<Goal> getGoals() {
        return goalRepository.findAll();
    }
}
