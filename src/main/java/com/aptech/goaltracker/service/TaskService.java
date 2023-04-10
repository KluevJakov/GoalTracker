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
        taskRepository.save(task);
    }

    public void complete(Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        task.setSuccess(true);
        taskRepository.save(task);

        List<Goal> goalList = goalRepository.findAll();

        Goal goal = goalList.stream().filter(e -> e.getTasks().stream().anyMatch(j -> j.getId().equals(taskId))).findFirst().get();

        if (goal.getTasks().stream().allMatch(Task::getSuccess)) {
            goal.setSuccess(true);
            goalRepository.save(goal);
        }
    }

    @Transactional
    public void deleteTask(Long taskId) {
        taskRepository.deleteLinks(taskId);
        taskRepository.deleteById(taskId);
    }

    public List<Task> getTasksByGoalId(Long id) {
        Goal goal = goalRepository.findById(id).get();
        return goal.getTasks();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }
}
