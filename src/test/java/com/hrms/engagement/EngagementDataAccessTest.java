package com.hrms.engagement;

import com.hrms.engagement.entity.*;
import com.hrms.engagement.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EngagementDataAccessTest {

    @Autowired
    private HelpdeskTicketRepository helpdeskTicketRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private EmployeeGrievanceRepository employeeGrievanceRepository;

    @Autowired
    private EmployeeDocumentRepository employeeDocumentRepository;

    @Autowired
    private GatePassRepository gatePassRepository;

    @Test
    void shouldSaveAndLoadEngagementEntities() {
        HelpdeskTicket ticket = new HelpdeskTicket();
        ticket.setEmployeeId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        ticket.setSubject("System access issue");
        ticket.setDescription("Unable to access payroll portal");
        ticket.setCategory("IT");
        ticket.setPriority("High");
        ticket.setStatus("Open");

        HelpdeskTicket savedTicket = helpdeskTicketRepository.save(ticket);

        Announcement announcement = new Announcement();
        announcement.setTitle("Town hall");
        announcement.setContent("Quarterly town hall today at 5 PM.");
        announcement.setAuthorId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        announcement.setPriority("High");
        announcement.setActive(true);

        Announcement savedAnnouncement = announcementRepository.save(announcement);

        EmployeeGrievance grievance = new EmployeeGrievance();
        grievance.setEmployeeId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        grievance.setAgainstEmployeeId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        grievance.setSubject("Workload concerns");
        grievance.setDescription("Requested for workload redistribution");
        grievance.setStatus("Submitted");

        EmployeeGrievance savedGrievance = employeeGrievanceRepository.save(grievance);

        EmployeeDocument document = new EmployeeDocument();
        document.setEmployeeId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        document.setDocumentType("ID Proof");
        document.setFileName("aadhaar.pdf");
        document.setFilePath("/uploads/documents/aadhaar.pdf");
        document.setUploadedBy(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        document.setUploadedAt(LocalDateTime.now());

        EmployeeDocument savedDocument = employeeDocumentRepository.save(document);

        GatePass gatePass = new GatePass();
        gatePass.setEmployeeId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        gatePass.setCategory("Personal Work");
        gatePass.setReason("Travel to bank");
        gatePass.setStatus("Pending");

        GatePass savedGatePass = gatePassRepository.save(gatePass);

        assertThat(savedTicket.getId()).isNotNull();
        assertThat(helpdeskTicketRepository.findById(savedTicket.getId())).isPresent();
        assertThat(savedAnnouncement.getId()).isNotNull();
        assertThat(announcementRepository.findById(savedAnnouncement.getId())).isPresent();
        assertThat(savedGrievance.getId()).isNotNull();
        assertThat(employeeGrievanceRepository.findById(savedGrievance.getId())).isPresent();
        assertThat(savedDocument.getId()).isNotNull();
        assertThat(employeeDocumentRepository.findById(savedDocument.getId())).isPresent();
        assertThat(savedGatePass.getId()).isNotNull();
        assertThat(gatePassRepository.findById(savedGatePass.getId())).isPresent();
    }
}
