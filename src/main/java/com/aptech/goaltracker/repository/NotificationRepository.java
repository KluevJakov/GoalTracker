package com.aptech.goaltracker.repository;

import com.aptech.goaltracker.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
