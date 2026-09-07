package com.hrms.org.controller;

import com.hrms.org.service.*;
import com.hrms.org.dto.*;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/org")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public List<DepartmentResponse> listDepartments() {
        return departmentService.listDepartments();
    }

    @PostMapping("/departments")
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(departmentService.createDepartment(request));
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable UUID id) {
        return ResponseEntity.ok(departmentService.getDepartment(id));
    }
}
