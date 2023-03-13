package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
