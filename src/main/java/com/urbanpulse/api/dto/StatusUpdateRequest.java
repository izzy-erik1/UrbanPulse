package com.urbanpulse.api.dto;

import com.urbanpulse.model.enums.IssueStatus;

public class StatusUpdateRequest {
    private IssueStatus status;

    public IssueStatus getStatus() { return status; }
    public void setStatus(IssueStatus status) { this.status = status; }
}