package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.User;
import com.aptech.goaltracker.models.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM TUSER WHERE LOGIN = ?1")
    int countUserByLogin(String login);

    User findByLogin(String login);

    @Query(nativeQuery = true, value = "SELECT * FROM TUSER WHERE LOGIN LIKE %?1%")
    List<User> searchUsersByLogin(String login);
}
