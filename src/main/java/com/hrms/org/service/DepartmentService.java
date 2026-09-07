package com.hrms.org.service;

import com.hrms.org.entity.*;
import com.hrms.org.repository.*;
import com.hrms.org.dto.*;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentResponse> listDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(dept -> new DepartmentResponse(dept.getId().toString(), dept.getName()))
                .toList();
    }

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.name());
        Department saved = departmentRepository.save(department);
        return new DepartmentResponse(saved.getId().toString(), saved.getName());
    }

    public DepartmentResponse getDepartment(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + id));
        return new DepartmentResponse(department.getId().toString(), department.getName());
    }
}
