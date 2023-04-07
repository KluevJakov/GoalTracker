package com.aptech.goaltracker.service;

import com.aptech.goaltracker.models.Goal;
import com.aptech.goaltracker.models.Team;
import com.aptech.goaltracker.models.User;
import com.aptech.goaltracker.repository.GoalRepository;
import com.aptech.goaltracker.repository.TaskRepository;
import com.aptech.goaltracker.repository.TeamRepository;
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
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TaskRepository taskRepository;

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

    @Transactional
    public void createForTeam(Goal goal) {
        Team team = teamRepository.findById(goal.getId()).get();
        goal.setId(null);
        goal.setSuccess(false);
        Goal createdGoal = goalRepository.save(goal);
        if (team.getGoals() == null) {
            team.setGoals(new ArrayList<>());
        }
        team.getGoals().add(createdGoal);
        teamRepository.save(team);
    }

    public void updateGoal(Goal goal) {
        Goal goalFromDb = goalRepository.findById(goal.getId()).get();
        goal.setTasks(goalFromDb.getTasks());
        goal.setSuccess(goalFromDb.getSuccess());
        goalRepository.save(goal);
    }

    @Transactional
    public void deleteGoal(Long goalId) {
        Goal goal = goalRepository.findById(goalId).get();

        goal.getTasks().stream().forEach(j -> {
            taskRepository.deleteLinks(j.getId());
            taskRepository.deleteById(j.getId());
        });

        goalRepository.deleteLinks(goalId);
        goalRepository.deleteById(goalId);
    }

    public List<Goal> getGoalsByUserId(Long id) {
        return goalRepository.getGoalsByUserId(id);
    }
    public List<Goal> getGoalsByTeamId(Long id) {
        return goalRepository.getGoalsByTeamId(id);
    }


    public Goal getGoalById(Long id) {
        return goalRepository.findById(id).get();
    }

    public List<Goal> getGoals() {
        return goalRepository.findAll();
    }
}
