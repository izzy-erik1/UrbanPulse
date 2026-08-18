package com.urbanpulse.api.dto;

import com.urbanpulse.model.Comment;

public class CommentResponse {

    private final Long id;
    private final String content;
    private final String authorName;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.authorName = comment.getAuthor().getName();
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public String getAuthorName() { return authorName; }
}