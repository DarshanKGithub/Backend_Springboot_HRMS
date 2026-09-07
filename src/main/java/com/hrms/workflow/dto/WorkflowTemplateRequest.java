package com.hrms.workflow.dto;

public record WorkflowTemplateRequest(String name, String triggerEvent, String stepsJson) {
}
