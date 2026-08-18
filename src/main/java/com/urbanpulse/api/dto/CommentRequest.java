package com.urbanpulse.api.dto;

public class CommentRequest {

    private String content;
    private Long issueId;

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getIssueId() { return issueId; }
    public void setIssueId(Long issueId) { this.issueId = issueId; }
}