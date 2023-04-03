package com.aptech.goaltracker.service;

import com.aptech.goaltracker.models.Goal;
import com.aptech.goaltracker.models.Task;
import com.aptech.goaltracker.models.User;
import com.aptech.goaltracker.repository.GoalRepository;
import com.aptech.goaltracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public void createTask(Task task) {
        Goal goal = goalRepository.findById(task.getId()).get();
        task.setId(null);
        task.setSuccess(false);
        Task createdTask = taskRepository.save(task);
        if (goal.getTasks() == null) {
            goal.setTasks(new ArrayList<>());
        }
        goal.getTasks().add(createdTask);
        goalRepository.save(goal);
    }

    public void updateTask(Task task) {
        task.setSuccess(false);
        taskRepository.save(task);
    }

    public List<Task> getTasksByGoalId(Long id) {
        Goal goal = goalRepository.findById(id).get();
        return goal.getTasks();
    }

    public Task geTaskById(Long id) {
        return taskRepository.findById(id).get();
    }
}
