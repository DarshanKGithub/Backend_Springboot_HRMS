package com.hrms.engagement.controller;

import com.hrms.engagement.dto.EmployeeDocumentResponse;
import com.hrms.engagement.service.EmployeeDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
public class EmployeeDocumentController {
    private final EmployeeDocumentService documentService;
    public EmployeeDocumentController(EmployeeDocumentService documentService) { this.documentService = documentService; }
    @GetMapping public List<EmployeeDocumentResponse> list(@RequestParam(required = false) UUID employeeId) { return documentService.list(employeeId); }
    @PostMapping(consumes = "multipart/form-data") public ResponseEntity<EmployeeDocumentResponse> upload(@RequestParam UUID employeeId, @RequestParam UUID uploadedBy, @RequestParam String documentType, @RequestPart MultipartFile file) { return ResponseEntity.ok(documentService.upload(employeeId, uploadedBy, documentType, file)); }
}
