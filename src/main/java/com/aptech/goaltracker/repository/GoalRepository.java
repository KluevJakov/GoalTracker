package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query(value = "SELECT * FROM GOAL G INNER JOIN TUSER_GOALS TG ON G.ID = TG.GOALS_ID INNER JOIN TUSER T ON T.ID = TG.USER_ID WHERE T.ID = ?1", nativeQuery = true)
    List<Goal> getGoalsByUserId(Long id);

    @Query(value = "SELECT * FROM GOAL G INNER JOIN TEAM_GOALS TG ON G.ID = TG.GOALS_ID INNER JOIN TEAM T ON T.ID = TG.TEAM_ID WHERE T.ID = ?1", nativeQuery = true)
    List<Goal> getGoalsByTeamId(Long id);

    @Query(value = "DELETE FROM TEAM_GOALS WHERE GOALS_ID = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteLinks(Long goalId);
}
