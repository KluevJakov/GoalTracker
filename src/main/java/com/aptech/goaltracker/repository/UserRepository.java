package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM TUSER WHERE LOGIN = ?1")
    int countUserByLogin(String login);

    User findByLogin(String login);
}
