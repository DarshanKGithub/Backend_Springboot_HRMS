package com.hrms.workflow.repository;

import com.hrms.workflow.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface WorkflowTemplateRepository extends JpaRepository<WorkflowTemplate, UUID> {
	List<WorkflowTemplate> findByActiveTrue();
	List<WorkflowTemplate> findByTriggerEventAndActiveTrue(String triggerEvent);
}
