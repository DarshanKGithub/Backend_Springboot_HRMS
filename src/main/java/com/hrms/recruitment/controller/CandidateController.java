package com.hrms.recruitment.controller;

import com.hrms.recruitment.service.*;
import com.hrms.recruitment.dto.*;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recruitment")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/candidates")
    public List<CandidateResponse> listCandidates() {
        return candidateService.listCandidates();
    }

    @PostMapping("/candidates")
    public ResponseEntity<CandidateResponse> createCandidate(@Valid @RequestBody CandidateRequest request) {
        return ResponseEntity.ok(candidateService.createCandidate(request));
    }

    @GetMapping("/candidates/{id}")
    public ResponseEntity<CandidateResponse> getCandidate(@PathVariable UUID id) {
        return ResponseEntity.ok(candidateService.getCandidate(id));
    }
}
