package com.urbanpulse.api.controller;

import com.urbanpulse.api.dto.IssueRequest;
import com.urbanpulse.api.dto.IssueResponse;
import com.urbanpulse.model.Issue;
import com.urbanpulse.model.User;
import com.urbanpulse.service.IssueService;
import com.urbanpulse.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import com.urbanpulse.api.dto.StatusUpdateRequest;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;
    private final UserService userService;

    public IssueController(IssueService issueService, UserService userService) {
        this.issueService = issueService;
        this.userService = userService;
    }

    @GetMapping
    public List<IssueResponse> getAll() {
        return issueService.getAll().stream()
                .map(IssueResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public IssueResponse create(@RequestBody IssueRequest request, HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        User reporter = userService.getById(userId);

        Issue issue = issueService.createIssueFromIds(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                reporter,
                request.getMunicipalityId(),
                request.getCategoryId()
        );

        return new IssueResponse(issue);
    }
    @PatchMapping("/{id}/status")
    public IssueResponse updateStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) {
        Issue updated = issueService.changeStatus(id, request.getStatus());
        return new IssueResponse(updated);
    }
}