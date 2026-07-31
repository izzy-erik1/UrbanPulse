package com.urbanpulse.repository.impl.hibernate;

import com.urbanpulse.model.Issue;
import com.urbanpulse.model.enums.IssueStatus;
import com.urbanpulse.repository.IssueRepository;
import com.urbanpulse.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class HibernateIssueRepository implements IssueRepository {

    @Override
    public Optional<Issue> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Issue.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Issue> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Issue i", Issue.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Issue save(Issue entity) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } finally {
            em.close();
        }
    }

    @Override
    public Issue update(Issue entity) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Issue merged = em.merge(entity);
            em.getTransaction().commit();
            return merged;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Issue issue = em.find(Issue.class, id);
            if (issue != null) {
                em.remove(issue);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Issue> findByMunicipality(Long municipalityId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Issue i WHERE i.municipality.id = :municipalityId", Issue.class)
                    .setParameter("municipalityId", municipalityId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Issue> findByStatus(IssueStatus status) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Issue i WHERE i.status = :status", Issue.class)
                    .setParameter("status", status)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Issue> findByCategory(Long categoryId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT i FROM Issue i WHERE i.category.id = :categoryId", Issue.class)
                    .setParameter("categoryId", categoryId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}