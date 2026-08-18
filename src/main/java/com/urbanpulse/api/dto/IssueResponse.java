package com.urbanpulse.api.dto;

import com.urbanpulse.model.Issue;

public class IssueResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final String status;
    private final String priority;
    private final String reporterName;
    private final String municipalityName;
    private final String categoryName;

    public IssueResponse(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.description = issue.getDescription();
        this.status = issue.getStatus().name();
        this.priority = issue.getPriority().name();
        this.reporterName = issue.getReporter().getName();
        this.municipalityName = issue.getMunicipality().getName();
        this.categoryName = issue.getCategory().getName();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public String getReporterName() { return reporterName; }
    public String getMunicipalityName() { return municipalityName; }
    public String getCategoryName() { return categoryName; }
}