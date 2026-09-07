package com.hrms.notifications.repository;

import com.hrms.notifications.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, UUID> {
}
