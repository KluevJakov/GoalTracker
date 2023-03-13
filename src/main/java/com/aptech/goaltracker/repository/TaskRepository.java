package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
