package com.urbanpulse.api.controller;

import com.urbanpulse.api.dto.CommentRequest;
import com.urbanpulse.api.dto.CommentResponse;
import com.urbanpulse.model.Comment;
import com.urbanpulse.model.User;
import com.urbanpulse.service.CommentService;
import com.urbanpulse.service.IssueService;
import com.urbanpulse.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final IssueService issueService;
    private final UserService userService;

    public CommentController(CommentService commentService, IssueService issueService, UserService userService) {
        this.commentService = commentService;
        this.issueService = issueService;
        this.userService = userService;
    }

    @GetMapping("/issue/{issueId}")
    public List<CommentResponse> getByIssue(@PathVariable Long issueId) {
        return commentService.getByIssue(issueId).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CommentResponse create(@RequestBody CommentRequest request, HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        User author = userService.getById(userId);

        Comment comment = commentService.addCommentFromIssueId(
                request.getContent(), request.getIssueId(), author, issueService);

        return new CommentResponse(comment);
    }
}