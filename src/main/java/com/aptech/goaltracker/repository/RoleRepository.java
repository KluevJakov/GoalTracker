package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
