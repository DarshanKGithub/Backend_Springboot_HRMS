package com.hrms.payroll.service;

import com.hrms.payroll.dto.*;
import com.hrms.payroll.entity.*;
import com.hrms.payroll.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PayrollService {
    private final SalaryRepository salaryRepository;
    private final SalaryComponentRepository salaryComponentRepository;
    private final PayslipRepository payslipRepository;
    private final PayslipComponentRepository payslipComponentRepository;

    public PayrollService(SalaryRepository salaryRepository,
                          SalaryComponentRepository salaryComponentRepository,
                          PayslipRepository payslipRepository,
                          PayslipComponentRepository payslipComponentRepository) {
        this.salaryRepository = salaryRepository;
        this.salaryComponentRepository = salaryComponentRepository;
        this.payslipRepository = payslipRepository;
        this.payslipComponentRepository = payslipComponentRepository;
    }

    public List<SalaryComponentResponse> listComponents() {
        return salaryComponentRepository.findAll().stream().map(this::toResponse).toList();
    }

    public SalaryComponentResponse createComponent(SalaryComponentRequest request) {
        SalaryComponent component = new SalaryComponent();
        component.setName(request.name());
        component.setComponentType(request.componentType());
        component.setDescription(request.description());
        component.setActive(request.active());
        return toResponse(salaryComponentRepository.save(component));
    }

    public SalaryComponentResponse updateComponent(UUID id, SalaryComponentRequest request) {
        SalaryComponent component = salaryComponentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Salary component not found: " + id));
        component.setName(request.name());
        component.setComponentType(request.componentType());
        component.setDescription(request.description());
        component.setActive(request.active());
        return toResponse(salaryComponentRepository.save(component));
    }

    public void deleteComponent(UUID id) {
        if (!salaryComponentRepository.existsById(id)) {
            throw new IllegalArgumentException("Salary component not found: " + id);
        }
        salaryComponentRepository.deleteById(id);
    }

    public SalaryResponse getSalary(UUID employeeId) {
        Salary salary = salaryRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Salary not found for employee: " + employeeId));
        return new SalaryResponse(salary.getId(), salary.getEmployeeId(), salary.getBaseSalary(), salary.getGrade(),
                salary.getCurrency(), salary.getEffectiveFrom(), salary.getEffectiveTo(), salary.isActive());
    }

    public PayslipResponse createPayslip(PayslipCreateRequest request) {
        Salary salary = salaryRepository.findByEmployeeId(request.employeeId())
                .orElseThrow(() -> new IllegalArgumentException("Salary not found for employee: " + request.employeeId()));
        BigDecimal gross = salary.getBaseSalary();
        Payslip payslip = new Payslip();
        payslip.setEmployeeId(request.employeeId());
        payslip.setMonth(request.month());
        payslip.setYear(request.year());
        payslip.setBaseSalary(salary.getBaseSalary());
        payslip.setGrossSalary(gross);
        payslip.setNetSalary(gross);
        return toResponse(payslipRepository.save(payslip));
    }

    public List<PayslipResponse> listPayslips(UUID employeeId, Integer month, Integer year) {
        return payslipRepository.findAll().stream()
                .filter(payslip -> employeeId == null || employeeId.equals(payslip.getEmployeeId()))
                .filter(payslip -> month == null || month == payslip.getMonth())
                .filter(payslip -> year == null || year == payslip.getYear())
                .map(this::toResponse).toList();
    }

    public PayslipDetailResponse getPayslip(UUID id) {
        Payslip payslip = payslipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payslip not found: " + id));
        List<PayslipComponentResponse> components = payslipComponentRepository.findAll().stream()
                .filter(component -> id.equals(component.getPayslipId()))
                .map(component -> new PayslipComponentResponse(component.getId(), component.getComponentName(),
                        component.getComponentType(), component.getAmount())).toList();
        return new PayslipDetailResponse(payslip.getId(), payslip.getEmployeeId(), payslip.getMonth(), payslip.getYear(),
                payslip.getBaseSalary(), payslip.getGrossSalary(), payslip.getTotalDeductions(), payslip.getTotalTax(),
                payslip.getNetSalary(), payslip.getDaysWorked(), payslip.getDaysAbsent(), payslip.getStatus(),
                payslip.getProcessedBy(), payslip.getProcessedAt(), payslip.getCreatedAt(), payslip.getUpdatedAt(), components);
    }

    private SalaryComponentResponse toResponse(SalaryComponent component) {
        return new SalaryComponentResponse(component.getId(), component.getName(), component.getComponentType(),
                component.getDescription(), component.isActive());
    }

    private PayslipResponse toResponse(Payslip payslip) {
        return new PayslipResponse(payslip.getId(), payslip.getEmployeeId(), payslip.getMonth(), payslip.getYear(),
                payslip.getBaseSalary(), payslip.getGrossSalary(), payslip.getTotalDeductions(), payslip.getTotalTax(),
                payslip.getNetSalary(), payslip.getDaysWorked(), payslip.getDaysAbsent(), payslip.getStatus(),
                payslip.getProcessedBy(), payslip.getProcessedAt(), payslip.getCreatedAt(), payslip.getUpdatedAt());
    }
}
