package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM TUSER WHERE LOGIN = ?1")
    int findByLogin(String login);
}
