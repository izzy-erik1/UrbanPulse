package com.urbanpulse.repository.impl.hibernate;

import com.urbanpulse.model.Comment;
import com.urbanpulse.repository.CommentRepository;
import com.urbanpulse.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class HibernateCommentRepository implements CommentRepository {

    @Override
    public Optional<Comment> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Comment.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Comment> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Comment save(Comment entity) {
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
    public Comment update(Comment entity) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Comment merged = em.merge(entity);
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
            Comment comment = em.find(Comment.class, id);
            if (comment != null) {
                em.remove(comment);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Comment> findByIssue(Long issueId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM Comment c WHERE c.issue.id = :issueId", Comment.class)
                    .setParameter("issueId", issueId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}