package com.aptech.goaltracker.controller;

import com.aptech.goaltracker.models.Task;
import com.aptech.goaltracker.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/getTasksByGoalId")
    public List<Task> getTasksByGoalId(@RequestParam(value = "id") Long id) {
        return taskService.getTasksByGoalId(id);
    }

    @GetMapping(value = "/complete")
    public void complete(@RequestParam(value = "id") Long id) {
        taskService.complete(id);
    }

    @GetMapping(value = "/getTaskById")
    public Task getTaskById(@RequestParam(value = "id") Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestBody Task task) {
        try {
            taskService.createTask(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody Task task) {
        try {
            taskService.updateTask(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) {
        try {
            taskService.deleteTask(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("");
    }
}
