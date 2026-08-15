package com.urbanpulse.service;

import com.urbanpulse.exception.InvalidIssueException;
import com.urbanpulse.model.Comment;
import com.urbanpulse.model.Issue;
import com.urbanpulse.model.User;
import com.urbanpulse.repository.CommentRepository;
import com.urbanpulse.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(String content, Issue issue, User author) {
        if (!ValidationUtil.isNotBlank(content)) {
            throw new InvalidIssueException("Comment content cannot be empty");
        }
        Comment comment = new Comment(content, issue, author);
        return commentRepository.save(comment);
    }

    public List<Comment> getByIssue(Long issueId) {
        return commentRepository.findByIssue(issueId);
    }
}