package com.urbanpulse.service;

import com.urbanpulse.exception.InvalidIssueException;
import com.urbanpulse.exception.ResourceNotFoundException;
import com.urbanpulse.model.Category;
import com.urbanpulse.model.Issue;
import com.urbanpulse.model.Municipality;
import com.urbanpulse.model.User;
import com.urbanpulse.model.enums.IssueStatus;
import com.urbanpulse.model.enums.Priority;
import com.urbanpulse.repository.IssueRepository;
import com.urbanpulse.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Issue createIssue(String title, String description, Priority priority,
                             User reporter, Municipality municipality, Category category) {
        if (!ValidationUtil.isNotBlank(title)) {
            throw new InvalidIssueException("Issue title is required");
        }
        Issue issue = new Issue(title, description, IssueStatus.SUBMITTED, priority,
                reporter, municipality, category);
        return issueRepository.save(issue);
    }

    public Issue getById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found: " + id));
    }

    public Issue changeStatus(Long id, IssueStatus newStatus) {
        Issue issue = getById(id);
        issue.setStatus(newStatus);
        return issueRepository.update(issue);
    }

    public List<Issue> getByMunicipality(Long municipalityId) {
        return issueRepository.findByMunicipality(municipalityId);
    }

    public List<Issue> getByStatus(IssueStatus status) {
        return issueRepository.findByStatus(status);
    }

    public List<Issue> getByCategory(Long categoryId) {
        return issueRepository.findByCategory(categoryId);
    }

    public List<Issue> getAll() {
        return issueRepository.findAll();
    }

    public java.util.List<Category> getAllCategories() {
        jakarta.persistence.EntityManager em = com.urbanpulse.util.HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        } finally {
            em.close();
        }
    }
}