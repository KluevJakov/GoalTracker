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
public class TeamService {

    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TaskRepository taskRepository;


    @Transactional
    public void createTeam(Team team, User user) {
        team.setInitiator(user);
        teamRepository.save(team);
    }

    public void updateTeam(Team team) {
        Team teamFromDb = teamRepository.findById(team.getId()).get();
        team.setGoals(teamFromDb.getGoals());
        team.setInitiator(teamFromDb.getInitiator());
        teamRepository.save(team);
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).get();

        team.getGoals().stream().forEach(e -> {
            e.getTasks().stream().forEach(j -> {
                taskRepository.deleteById(j.getId());
            });
            goalRepository.deleteById(e.getId());
        });

        teamRepository.deleteById(teamId);
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).get();
    }
    public List<Team> getTeamByInitiatorId(Long id) {
        return teamRepository.getTeamByInitiatorId(id);
    }


}
