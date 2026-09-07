package com.hrms.workflow.service;

import com.hrms.workflow.entity.*;
import com.hrms.workflow.repository.*;
import com.hrms.workflow.dto.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class WorkflowService {
    private final FormTemplateRepository formTemplateRepository;
    private final WorkflowTemplateRepository workflowTemplateRepository;
    private final WorkflowInstanceRepository workflowInstanceRepository;
    private final ObjectMapper objectMapper;

    public WorkflowService(FormTemplateRepository formTemplateRepository,
                           WorkflowTemplateRepository workflowTemplateRepository,
                           WorkflowInstanceRepository workflowInstanceRepository,
                           ObjectMapper objectMapper) {
        this.formTemplateRepository = formTemplateRepository;
        this.workflowTemplateRepository = workflowTemplateRepository;
        this.workflowInstanceRepository = workflowInstanceRepository;
        this.objectMapper = objectMapper;
    }

    public FormTemplateResponse createFormTemplate(FormTemplateRequest request) {
        validateJson(request.schemaJson(), "schema_json");
        FormTemplate form = new FormTemplate();
        form.setName(request.name());
        form.setDescription(request.description());
        form.setSchemaJson(request.schemaJson());
        return toResponse(formTemplateRepository.save(form));
    }

    public List<FormTemplateResponse> listFormTemplates() {
        return formTemplateRepository.findByActiveTrue().stream().map(this::toResponse).toList();
    }

    public WorkflowTemplateResponse createWorkflowTemplate(WorkflowTemplateRequest request) {
        validateJson(request.stepsJson(), "steps_json");
        WorkflowTemplate template = new WorkflowTemplate();
        template.setName(request.name());
        template.setTriggerEvent(request.triggerEvent());
        template.setStepsJson(request.stepsJson());
        return toResponse(workflowTemplateRepository.save(template));
    }

    public List<WorkflowTemplateResponse> listWorkflowTemplates() {
        return workflowTemplateRepository.findByActiveTrue().stream().map(this::toResponse).toList();
    }

    public List<WorkflowInstanceResponse> triggerWorkflow(String eventName, UUID targetId) {
        return workflowTemplateRepository.findByTriggerEventAndActiveTrue(eventName).stream().map(template -> {
            WorkflowInstance instance = new WorkflowInstance();
            instance.setTemplateId(template.getId());
            instance.setTargetId(targetId);
            return toResponse(workflowInstanceRepository.save(instance));
        }).toList();
    }

    public WorkflowInstanceResponse advanceWorkflow(UUID instanceId) {
        WorkflowInstance instance = workflowInstanceRepository.findById(instanceId)
                .orElseThrow(() -> new IllegalArgumentException("Workflow instance not found: " + instanceId));
        WorkflowTemplate template = workflowTemplateRepository.findById(instance.getTemplateId()).orElse(null);
        if (template != null) {
            int stepCount = jsonArraySize(template.getStepsJson());
            if (instance.getCurrentStep() < stepCount - 1) {
                instance.setCurrentStep(instance.getCurrentStep() + 1);
            } else {
                instance.setStatus("Completed");
            }
            instance.setUpdatedAt(LocalDateTime.now());
            workflowInstanceRepository.save(instance);
        }
        return toResponse(instance);
    }

    private void validateJson(String value, String field) {
        try {
            objectMapper.readTree(value);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid " + field);
        }
    }

    private int jsonArraySize(String value) {
        try {
            return objectMapper.readTree(value).size();
        } catch (Exception exception) {
            return 0;
        }
    }

    private FormTemplateResponse toResponse(FormTemplate form) {
        return new FormTemplateResponse(form.getId(), form.getName(), form.getDescription(), form.getSchemaJson(), form.isActive(), form.getCreatedAt());
    }

    private WorkflowTemplateResponse toResponse(WorkflowTemplate template) {
        return new WorkflowTemplateResponse(template.getId(), template.getName(), template.getTriggerEvent(), template.getStepsJson(), template.isActive(), template.getCreatedAt());
    }

    private WorkflowInstanceResponse toResponse(WorkflowInstance instance) {
        return new WorkflowInstanceResponse(instance.getId(), instance.getTemplateId(), instance.getTargetId(), instance.getCurrentStep(), instance.getStatus(), instance.getCreatedAt(), instance.getUpdatedAt());
    }
}
