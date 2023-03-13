package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
