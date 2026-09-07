package com.hrms.leave.controller;

import com.hrms.leave.service.*;
import com.hrms.leave.dto.*;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leave")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping("/requests")
    public List<LeaveRequestResponse> listLeaveRequests() {
        return leaveRequestService.listLeaveRequests();
    }

    @PostMapping("/requests")
    public ResponseEntity<LeaveRequestResponse> createLeaveRequest(@Valid @RequestBody LeaveRequestRequest request) {
        return ResponseEntity.ok(leaveRequestService.createLeaveRequest(request));
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<LeaveRequestResponse> getLeaveRequest(@PathVariable UUID id) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequest(id));
    }
}
