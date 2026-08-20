package com.urbanpulse.api.controller;

import com.urbanpulse.api.dto.CategoryResponse;
import com.urbanpulse.api.dto.MunicipalityResponse;
import com.urbanpulse.service.IssueService;
import com.urbanpulse.service.MunicipalityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LookupController {

    private final IssueService issueService;
    private final MunicipalityService municipalityService;

    public LookupController(IssueService issueService, MunicipalityService municipalityService) {
        this.issueService = issueService;
        this.municipalityService = municipalityService;
    }

    @GetMapping("/api/categories")
    public List<CategoryResponse> getCategories() {
        return issueService.getAllCategories().stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/municipalities")
    public List<MunicipalityResponse> getMunicipalities() {
        return municipalityService.getAll().stream()
                .map(MunicipalityResponse::new)
                .collect(Collectors.toList());
    }
}