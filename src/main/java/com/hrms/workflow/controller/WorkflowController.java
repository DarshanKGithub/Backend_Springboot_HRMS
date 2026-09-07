package com.hrms.workflow.controller;

import com.hrms.workflow.service.*;
import com.hrms.workflow.dto.*;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {
    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/forms")
    public ResponseEntity<FormTemplateResponse> createForm(@RequestBody FormTemplateRequest request) {
        return ResponseEntity.ok(workflowService.createFormTemplate(request));
    }

    @GetMapping("/forms")
    public List<FormTemplateResponse> listForms() {
        return workflowService.listFormTemplates();
    }

    @PostMapping("/templates")
    public ResponseEntity<WorkflowTemplateResponse> createTemplate(@RequestBody WorkflowTemplateRequest request) {
        return ResponseEntity.ok(workflowService.createWorkflowTemplate(request));
    }

    @GetMapping("/templates")
    public List<WorkflowTemplateResponse> listTemplates() {
        return workflowService.listWorkflowTemplates();
    }

    @PostMapping("/trigger/{eventName}")
    public List<WorkflowInstanceResponse> trigger(@PathVariable String eventName, @RequestParam UUID targetId) {
        return workflowService.triggerWorkflow(eventName, targetId);
    }

    @PostMapping("/instances/{instanceId}/advance")
    public ResponseEntity<WorkflowInstanceResponse> advance(@PathVariable UUID instanceId) {
        return ResponseEntity.ok(workflowService.advanceWorkflow(instanceId));
    }
}
