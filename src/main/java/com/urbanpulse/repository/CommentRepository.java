package com.urbanpulse.repository;

import com.urbanpulse.model.Comment;
import java.util.List;

public interface CommentRepository extends Repository<Comment, Long> {

    List<Comment> findByIssue(Long issueId);
}