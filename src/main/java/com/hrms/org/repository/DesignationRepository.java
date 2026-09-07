package com.hrms.org.repository;

import com.hrms.org.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DesignationRepository extends JpaRepository<Designation, UUID> {
}
