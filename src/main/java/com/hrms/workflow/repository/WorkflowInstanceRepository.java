package com.hrms.workflow.repository;

import com.hrms.workflow.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, UUID> {
}
