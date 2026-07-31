package com.urbanpulse.repository;

import com.urbanpulse.model.Issue;
import com.urbanpulse.model.enums.IssueStatus;
import java.util.List;

public interface IssueRepository extends Repository<Issue, Long> {

    List<Issue> findByMunicipality(Long municipalityId);

    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByCategory(Long categoryId);
}