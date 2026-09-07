package com.hrms.attendance.repository;

import com.hrms.attendance.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BiometricDeviceRepository extends JpaRepository<BiometricDevice, UUID> {
}
