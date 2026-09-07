package com.hrms.dashboard.service;

import com.hrms.attendance.entity.Attendance;
import com.hrms.attendance.repository.AttendanceRepository;
import com.hrms.dashboard.dto.DashboardResponse;
import com.hrms.employee.entity.Employee;
import com.hrms.employee.repository.EmployeeRepository;
import com.hrms.leave.entity.LeaveRequest;
import com.hrms.leave.repository.LeaveRequestRepository;
import com.hrms.org.entity.Department;
import com.hrms.org.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DashboardService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    public DashboardService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, AttendanceRepository attendanceRepository, LeaveRequestRepository leaveRequestRepository) {
        this.employeeRepository = employeeRepository; this.departmentRepository = departmentRepository; this.attendanceRepository = attendanceRepository; this.leaveRequestRepository = leaveRequestRepository;
    }

    public DashboardResponse summary(LocalDate startDate, LocalDate endDate, UUID departmentId) {
        LocalDate start = startDate == null ? LocalDate.now().withDayOfMonth(1) : startDate;
        LocalDate end = endDate == null ? LocalDate.now() : endDate;
        if (start.isAfter(end)) throw new IllegalArgumentException("startDate must be before endDate");
        List<Employee> employees = employeeRepository.findAll().stream().filter(item -> departmentId == null || departmentId.equals(item.getDepartmentId())).toList();
        List<Attendance> attendance = attendanceRepository.findAll().stream().filter(item -> !item.getAttendanceDate().isBefore(start) && !item.getAttendanceDate().isAfter(end)).filter(item -> employees.stream().anyMatch(employee -> employee.getId().equals(item.getEmployeeId()))).toList();
        long pending = leaveRequestRepository.findAll().stream().filter(item -> "pending".equalsIgnoreCase(item.getStatus())).count();
        List<DashboardResponse.DepartmentBreakdown> breakdown = departmentRepository.findAll().stream().map(department -> {
            List<Employee> inDepartment = employees.stream().filter(employee -> department.getId().equals(employee.getDepartmentId())).toList();
            return new DashboardResponse.DepartmentBreakdown(department.getId(), department.getName(), inDepartment.size(), inDepartment.stream().filter(Employee::isActive).count(), inDepartment.stream().filter(employee -> !employee.isActive()).count());
        }).toList();
        return new DashboardResponse(LocalDateTime.now(), new DashboardResponse.DashboardFilters(start, end, departmentId), new DashboardResponse.DashboardKpis(employees.size(), employees.stream().filter(Employee::isActive).count(), employees.stream().filter(employee -> !employee.isActive()).count(), departmentRepository.count(), pending), new DashboardResponse.AttendanceSummary("range", attendance.stream().filter(item -> "present".equalsIgnoreCase(item.getStatus())).count(), attendance.stream().filter(item -> "absent".equalsIgnoreCase(item.getStatus())).count(), attendance.stream().filter(Attendance::isLate).count()), breakdown);
    }
}
