package com.hrms.payroll.controller;

import com.hrms.payroll.dto.*;
import com.hrms.payroll.service.PayrollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payroll")
public class PayrollController {
    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @GetMapping("/components")
    public List<SalaryComponentResponse> listComponents() {
        return payrollService.listComponents();
    }

    @PostMapping("/components")
    public ResponseEntity<SalaryComponentResponse> createComponent(@RequestBody SalaryComponentRequest request) {
        return ResponseEntity.ok(payrollService.createComponent(request));
    }

    @PutMapping("/components/{id}")
    public ResponseEntity<SalaryComponentResponse> updateComponent(@PathVariable UUID id,
                                                                    @RequestBody SalaryComponentRequest request) {
        return ResponseEntity.ok(payrollService.updateComponent(id, request));
    }

    @DeleteMapping("/components/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable UUID id) {
        payrollService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/salary")
    public ResponseEntity<SalaryResponse> getSalary(@RequestParam UUID employeeId) {
        return ResponseEntity.ok(payrollService.getSalary(employeeId));
    }

    @PostMapping("/payslips")
    public ResponseEntity<PayslipResponse> createPayslip(@RequestBody PayslipCreateRequest request) {
        return ResponseEntity.ok(payrollService.createPayslip(request));
    }

    @GetMapping("/payslips")
    public List<PayslipResponse> listPayslips(@RequestParam(required = false) UUID employeeId,
                                              @RequestParam(required = false) Integer month,
                                              @RequestParam(required = false) Integer year) {
        return payrollService.listPayslips(employeeId, month, year);
    }

    @GetMapping("/payslips/{id}")
    public ResponseEntity<PayslipDetailResponse> getPayslip(@PathVariable UUID id) {
        return ResponseEntity.ok(payrollService.getPayslip(id));
    }
}
