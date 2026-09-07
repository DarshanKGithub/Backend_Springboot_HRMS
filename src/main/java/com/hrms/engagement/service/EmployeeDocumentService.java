package com.hrms.engagement.service;

import com.hrms.engagement.dto.EmployeeDocumentResponse;
import com.hrms.engagement.entity.EmployeeDocument;
import com.hrms.engagement.repository.EmployeeDocumentRepository;
import com.hrms.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeDocumentService {
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf", "png", "jpg", "jpeg", "doc", "docx");
    private final EmployeeDocumentRepository documentRepository;
    private final EmployeeRepository employeeRepository;
    private final Path uploadDirectory;

    public EmployeeDocumentService(EmployeeDocumentRepository documentRepository, EmployeeRepository employeeRepository, @Value("${app.upload.employee-documents-dir:uploads/employee_documents}") String uploadDirectory) {
        this.documentRepository = documentRepository; this.employeeRepository = employeeRepository; this.uploadDirectory = Path.of(uploadDirectory).toAbsolutePath().normalize();
    }
    public EmployeeDocumentResponse upload(UUID employeeId, UUID uploadedBy, String documentType, MultipartFile file) {
        if (!employeeRepository.existsById(employeeId)) throw new IllegalArgumentException("Employee not found: " + employeeId);
        if (file.isEmpty() || file.getOriginalFilename() == null) throw new IllegalArgumentException("File is required");
        String original = Path.of(file.getOriginalFilename()).getFileName().toString();
        String extension = original.contains(".") ? original.substring(original.lastIndexOf('.') + 1).toLowerCase() : "";
        if (!ALLOWED_EXTENSIONS.contains(extension)) throw new IllegalArgumentException("Unsupported document type");
        try { Files.createDirectories(uploadDirectory); String storedName = UUID.randomUUID() + "." + extension; Path target = uploadDirectory.resolve(storedName).normalize(); if (!target.startsWith(uploadDirectory)) throw new IllegalArgumentException("Invalid file path"); Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING); EmployeeDocument document = new EmployeeDocument(); document.setEmployeeId(employeeId); document.setUploadedBy(uploadedBy); document.setDocumentType(documentType); document.setFileName(original); document.setFilePath(uploadDirectory.relativize(target).toString()); return toResponse(documentRepository.save(document)); } catch (IOException exception) { throw new IllegalStateException("Unable to store document", exception); }
    }
    public List<EmployeeDocumentResponse> list(UUID employeeId) { return documentRepository.findAll().stream().filter(item -> employeeId == null || employeeId.equals(item.getEmployeeId())).map(this::toResponse).toList(); }
    private EmployeeDocumentResponse toResponse(EmployeeDocument item) { return new EmployeeDocumentResponse(item.getId(), item.getEmployeeId(), item.getDocumentType(), item.getFileName(), item.getFilePath(), item.getUploadedBy(), item.getUploadedAt()); }
}
