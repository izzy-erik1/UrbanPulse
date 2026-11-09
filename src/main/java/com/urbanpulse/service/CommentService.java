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
    private final NotificationService notificationService;

    public CommentService(CommentRepository commentRepository, NotificationService notificationService) {
        this.commentRepository = commentRepository;
        this.notificationService = notificationService;
    }

    public Comment addComment(String content, Issue issue, User author) {
        if (!ValidationUtil.isNotBlank(content)) {
            throw new InvalidIssueException("Comment content cannot be empty");
        }
        Comment saved = commentRepository.save(new Comment(content, issue, author));

        User reporter = issue.getReporter();
        if (reporter != null && !reporter.getId().equals(author.getId())) {
            notificationService.notify(
                    author.getName() + " commented on your issue: " + issue.getTitle(), reporter);
        }
        return saved;
    }

    public Comment addCommentFromIssueId(String content, Long issueId, User author, IssueService issueService) {
        Issue issue = issueService.getById(issueId);
        return addComment(content, issue, author);
    }

    public List<Comment> getByIssue(Long issueId) {
        return commentRepository.findByIssue(issueId);
    }
}