package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
