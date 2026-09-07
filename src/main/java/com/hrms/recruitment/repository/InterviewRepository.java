package com.hrms.recruitment.repository;

import com.hrms.recruitment.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InterviewRepository extends JpaRepository<Interview, UUID> {
}
