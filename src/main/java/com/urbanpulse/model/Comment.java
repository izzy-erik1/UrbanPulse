package com.urbanpulse.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    protected Comment() {}

    public Comment(String content, Issue issue, User author) {
        this.content = content;
        this.issue = issue;
        this.author = author;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Issue getIssue() { return issue; }
    public User getAuthor() { return author; }
}
