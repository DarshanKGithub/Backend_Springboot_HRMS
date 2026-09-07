package com.hrms.workflow.repository;

import com.hrms.workflow.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface FormTemplateRepository extends JpaRepository<FormTemplate, UUID> {
	List<FormTemplate> findByActiveTrue();
}
