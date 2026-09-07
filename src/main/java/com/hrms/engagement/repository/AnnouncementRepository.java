package com.hrms.engagement.repository;

import com.hrms.engagement.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
}
