package com.hrms.leave.service;

import com.hrms.leave.entity.*;
import com.hrms.leave.repository.*;
import com.hrms.leave.dto.*;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public List<LeaveRequestResponse> listLeaveRequests() {
        return leaveRequestRepository.findAll().stream()
                .map(LeaveRequestResponse::from)
                .toList();
    }

    public LeaveRequestResponse createLeaveRequest(LeaveRequestRequest request) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(request.employeeId());
        leaveRequest.setLeaveTypeId(request.leaveTypeId());
        leaveRequest.setStartDate(request.startDate());
        leaveRequest.setEndDate(request.endDate());
        leaveRequest.setDaysRequested(request.daysRequested());
        leaveRequest.setReason(request.reason());
        leaveRequest.setStatus(request.status() != null ? request.status() : "pending");

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        return LeaveRequestResponse.from(saved);
    }

    public LeaveRequestResponse getLeaveRequest(UUID id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found: " + id));
        return LeaveRequestResponse.from(leaveRequest);
    }
}
