package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.Goal;
import com.aptech.goaltracker.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "DELETE FROM GOAL_TASKS WHERE TASKS_ID = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteLinks(Long taskId);
}
