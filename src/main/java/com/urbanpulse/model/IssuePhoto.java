package com.urbanpulse.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "issue_photos")
public class IssuePhoto extends BaseEntity {

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    protected IssuePhoto() {}

    public IssuePhoto(String filePath, Issue issue) {
        this.filePath = filePath;
        this.uploadedAt = LocalDateTime.now();
        this.issue = issue;
    }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public Issue getIssue() { return issue; }
}