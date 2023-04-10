package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.Team;
import com.aptech.goaltracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM TEAM WHERE INITIATOR_ID = ?1")
    List<Team> getTeamByInitiatorId(Long id);

    @Query(value = "SELECT * FROM TUSER U INNER JOIN TEAM_MEMBERS M ON U.ID = M.MEMBERS_ID INNER JOIN TEAM T ON T.ID = M.TEAM_ID  WHERE T.ID = ?1", nativeQuery = true)
    List<User> getTeamMembers(Long teamId);
}
