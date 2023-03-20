package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query(value = "SELECT * FROM GOAL G INNER JOIN TUSER_GOALS TG ON G.ID = TG.GOALS_ID INNER JOIN TUSER T ON T.ID = TG.USER_ID WHERE T.ID = ?1", nativeQuery = true)
    List<Goal> getGoalsByUserId(Long id);
}
